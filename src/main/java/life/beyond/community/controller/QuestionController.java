package life.beyond.community.controller;

import life.beyond.community.dto.CommentDTO;
import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.enums.CommentTypeEnum;
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
        List<QuestionDTO> relatedQuestions = questionService.selectRelated(questionDTO);
        List<CommentDTO> comments = commentService.listByTargetId(id, CommentTypeEnum.QUESTION);

        model.addAttribute("question",questionDTO);
        model.addAttribute("relatedQuestions",relatedQuestions);
        model.addAttribute("comments",comments);
        return "question";
    }
}
