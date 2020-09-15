package life.beyond.community.controller;

import life.beyond.community.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.rmi.server.RemoteServer;

@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @RequestMapping("/registerPage")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam("username") String username,
                           @RequestParam("password") String password){
        registerService.register(username,password);
        return "redirect:/loginPage";
    }
}
