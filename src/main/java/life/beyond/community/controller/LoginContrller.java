package life.beyond.community.controller;

import life.beyond.community.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class LoginContrller {

    @Autowired
    LoginService loginService;

    @GetMapping("/loginPage")
    public String login(){
        return "login";
    }

    @PostMapping("/login")
    public String loginLocal(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             HttpServletResponse response,
                             Map<String,Object> map){
        boolean login = loginService.login(username, password, response);
        if(login==false) {
            map.put("msg","用户名密码错误");
            return "login";
        }

        return "redirect:/";
    }
}
