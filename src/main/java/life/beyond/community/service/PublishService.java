package life.beyond.community.service;

import life.beyond.community.mapper.QusetionMapper;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.Question;
import life.beyond.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Service
public class PublishService {

    @Autowired
    QusetionMapper qusetionMapper;

    @Autowired
    UserMapper userMapper;

    public boolean publish(String title, String description, String tag, HttpServletRequest request, Model model) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);

        if(title == null|| title == ""){
            model.addAttribute("error","标题不能为空");
            return false;
        }
        if(description == null|| description == ""){
            model.addAttribute("error","描述不能为空");
            return false;
        }
        if(tag == null|| tag == ""){
            model.addAttribute("error","标签不能为空");
            return false;
        }

        User user = null;

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

        if(user == null){
            model.addAttribute("error","用户未登录");
            return false;
        }


        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatorId(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());

        qusetionMapper.create(question);


        return true;
    }
}
