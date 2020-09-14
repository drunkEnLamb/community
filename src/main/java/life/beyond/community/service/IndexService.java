package life.beyond.community.service;

import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class IndexService {

    @Autowired
    UserMapper userMapper;
    //根据token验证登陆状态
    public void index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null){
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    System.out.println("success");
                    User user = userMapper.findByToken(token);
                    if(user !=null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
    }
}
