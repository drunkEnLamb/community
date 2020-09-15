package life.beyond.community.service;

import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class LoginService {

    @Autowired
    UserMapper userMapper;
    public boolean login(String username, String password, HttpServletResponse response) {
        User user = userMapper.findByNameAndPwd(username, password);
        if(user == null)
            return false;
        String token = user.getToken();
        response.addCookie(new Cookie("token",token));
        System.out.println();
        return true;
    }
}
