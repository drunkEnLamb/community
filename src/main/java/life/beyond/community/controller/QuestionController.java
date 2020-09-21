package life.beyond.community.controller;

import life.beyond.community.dto.CommentCreateDTO;
import life.beyond.community.dto.CommentDTO;
import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.service.CommentService;
import life.beyond.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
        questionService.incView(id);
        QuestionDTO questionDTO = questionService.question(id);
        List<CommentDTO> comments = commentService.listByQuestionId(id);

        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",comments);
        return "question";
    }
}
