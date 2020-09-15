package life.beyond.community.service;

import life.beyond.community.dto.AccessTokenDTO;
import life.beyond.community.dto.GithubUser;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.User;
import life.beyond.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
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
            User getUser = userMapper.findByAccountId(githubUser.getId());
            if(getUser == null){
                System.out.println("user is null");
                User user = new User();
                String token = UUID.randomUUID().toString();
                user.setToken(token);
                user.setName(githubUser.getName());
                user.setAccountId(String.valueOf(githubUser.getId()));
                user.setGmtCreate(System.currentTimeMillis());
                user.setGmtModified(user.getGmtCreate());
                user.setAvatarUrl(githubUser.getAvatar_url());
                userMapper.insert(user);
                response.addCookie(new Cookie("token",token));
            }
            else {
                System.out.println("user is not null");
                String token = getUser.getToken();
                System.out.println(response);
                response.addCookie(new Cookie("token",token));
            }

        }
    }
}
