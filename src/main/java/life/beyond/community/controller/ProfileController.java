package life.beyond.community.controller;

import life.beyond.community.dto.PaginationDTO;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.User;
import life.beyond.community.service.IndexService;
import life.beyond.community.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {

    @Autowired
    UserMapper userMapper;

    @Autowired
    ProfileService profileService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          Model model,
                          HttpServletRequest request,
                          @RequestParam(value = "page",defaultValue = "1") Integer page,
                          @RequestParam(value = "size",defaultValue = "5") Integer size){
//        User user = null;
        //登录态检测
//        Cookie[] cookies = request.getCookies();
//        if(cookies!=null){
//            for (Cookie cookie : cookies) {
//                if(cookie.getName().equals("token")){
//                    String token = cookie.getValue();
//                    user = userMapper.findByToken(token);
//                    if(user !=null){
//                        request.getSession().setAttribute("user", user);
//                    }
//                    break;
//                }
//            }
//        }

        User user = (User) request.getSession().getAttribute("user");

        if(user==null){
            return "redirect:/";
        }



        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
        }
        else if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
        }

        PaginationDTO pagination = profileService.list(user.getId(), page, size);
        model.addAttribute("pagination",pagination);

        return "profile";
    }
}