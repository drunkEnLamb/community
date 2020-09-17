package life.beyond.community.service;

import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.mapper.QusetionMapper;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.Question;
import life.beyond.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service
public class QuestionService {

    @Autowired
    QusetionMapper qusetionMapper;

    @Autowired
    UserMapper userMapper;

    public QuestionDTO question(Integer id) {
        Question question = qusetionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        User user = userMapper.findById(question.getCreatorId());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
