package life.beyond.community.controller;

import life.beyond.community.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.rmi.server.RemoteServer;
import java.util.Map;

@Controller
public class RegisterController {

    @Autowired
    RegisterService registerService;

    @GetMapping("/register")
    public String registerPage(){
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam(value = "username") String username,
                           @RequestParam(value = "password") String password,
                           Model model,
                           @RequestParam(value = "file")MultipartFile file){
        registerService.register(username,password,file);
        model.addAttribute("msg","注册成功，请登录");
        return "login";
    }

}
