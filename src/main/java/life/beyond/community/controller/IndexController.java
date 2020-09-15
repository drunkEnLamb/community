package life.beyond.community.controller;

import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

   @Autowired
    IndexService indexService;

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model){
        indexService.index(request);
        List<QuestionDTO> questionList = indexService.list();
        model.addAttribute("questions",questionList);
        return "index";
    }
}
