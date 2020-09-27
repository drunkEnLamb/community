package life.beyond.community.controller;

import life.beyond.community.dto.NotificationDTO;
import life.beyond.community.enums.NotificationTypeEnum;
import life.beyond.community.mapper.CommentMapper;
import life.beyond.community.model.User;
import life.beyond.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {

    @Autowired
    NotificationService notificationService;

    @GetMapping("notification/{id}")
    public String notification(@PathVariable(name = "id") Long id, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO = notificationService.read(id, user);

        if (NotificationTypeEnum.REPLY_QUESTION.getType() == notificationDTO.getType() ||
                NotificationTypeEnum.REPLY_COMMENT.getType() == notificationDTO.getType()) {
            return "redirect:/question/" + notificationDTO.getOuterId();
        }
        return "redirect:/";
    }
}
