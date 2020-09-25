package life.beyond.community.service;

import life.beyond.community.cache.TagCache;
import life.beyond.community.exception.CustomizeErrorCode;
import life.beyond.community.exception.CustomizeException;
import life.beyond.community.mapper.QuestionMapper;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.Question;
import life.beyond.community.model.QuestionExample;
import life.beyond.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;

@Service
public class PublishService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    UserMapper userMapper;

    public boolean publish(String title, String description, String tag, Long id, HttpServletRequest request, Model model) {

        model.addAttribute("title",title);
        model.addAttribute("description",description);
        model.addAttribute("tag",tag);
        model.addAttribute("id",id);



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
        String invalid = TagCache.filterInvalid(tag);
        if(StringUtils.isNotBlank(invalid)){
            model.addAttribute("error","输入非法标签："+invalid);
            return false;
        }

        User user = (User) request.getSession().getAttribute("user");

        if(user == null){
            model.addAttribute("error","用户未登录");
            return false;
        }
        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreatorId(user.getId());
        //创建
        if(id==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());

            questionMapper.insertSelective(question);
        }
        //更新
        else {
            question.setGmtModified(System.currentTimeMillis());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(id);
            int updated = questionMapper.updateByExampleSelective(question, questionExample);
            if(updated!=1){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }



        return true;
    }

    public void getById(Long id,Model model) {
        Question question = questionMapper.selectByPrimaryKey(id);
        model.addAttribute("title",question.getTitle());
        model.addAttribute("description",question.getDescription());
        model.addAttribute("tag",question.getTag());
        model.addAttribute("id",question.getId());
        model.addAttribute("tags", TagCache.get());
    }
}
