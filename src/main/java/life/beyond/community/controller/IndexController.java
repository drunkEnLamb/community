package life.beyond.community.controller;

import life.beyond.community.dto.PaginationDTO;
import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    IndexService indexService;

    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(value = "page",defaultValue = "1") Integer page,
                        @RequestParam(value = "size",defaultValue = "5") Integer size,
                        @RequestParam(value = "search",required = false) String search){
        //indexService.index(request);
        PaginationDTO pagination = indexService.list(search,page,size);
        model.addAttribute("pagination",pagination);
        model.addAttribute("search",search);
        return "index";
    }
}
