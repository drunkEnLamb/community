package life.beyond.community.service;

import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.User;
import life.beyond.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;
    public boolean login(String username, String password, HttpServletResponse response) {
        UserExample userExample = new UserExample();
        userExample.createCriteria()
                .andNameEqualTo(username)
                .andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);
        if(users.size() == 0)
            return false;
        String token = users.get(0).getToken();
        response.addCookie(new Cookie("token",token));
        System.out.println();
        return true;
    }
}
