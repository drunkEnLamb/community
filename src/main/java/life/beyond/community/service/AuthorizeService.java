package life.beyond.community.service;

import life.beyond.community.dto.AccessTokenDTO;
import life.beyond.community.dto.GithubUser;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.User;
import life.beyond.community.model.UserExample;
import life.beyond.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;

@Service
public class AuthorizeService {

    @Autowired
    GithubProvider githubProvider;

    @Autowired
    UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.client.redirect_uri}")
    private String redirectUri;

    public void callback(String code, String state, HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(redirectUri);
        accessTokenDTO.setState(state);
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getUser(accessToken);
        if(githubUser!=null){
            UserExample userExample = new UserExample();
            userExample.createCriteria().andAccountIdEqualTo(githubUser.getId());
            List<User> users = userMapper.selectByExample(userExample);
            if(users.size() == 0){
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccountId(githubUser.getId());
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatarUrl());
                userMapper.insert(user);
                response.addCookie(new Cookie("token",token));
            }
            else {
                String token = users.get(0).getToken();
                System.out.println(response);
                response.addCookie(new Cookie("token",token));
            }

        }
    }
}
