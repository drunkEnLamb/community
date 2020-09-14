package life.beyond.community.controller;

import life.beyond.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

   @Autowired
    IndexService indexService;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        indexService.index(request);
        return "index";
    }
}
