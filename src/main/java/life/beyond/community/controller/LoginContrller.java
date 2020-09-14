package life.beyond.community.controller;

import life.beyond.community.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class LoginContrller {

    @Autowired
    LoginService loginService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/userLogin")
    public String loginLocal(@RequestParam("username") String username,
                             @RequestParam("password") String password,
                             HttpServletResponse response){
        loginService.login(username,password,response);
        return "redirect:/";
    }
}
