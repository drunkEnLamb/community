package life.beyond.community.service;

import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.exception.CustomizeErrorCode;
import life.beyond.community.exception.CustomizeException;
import life.beyond.community.mapper.QuestionExtMapper;
import life.beyond.community.mapper.QuestionMapper;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.Question;
import life.beyond.community.model.QuestionExample;
import life.beyond.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    @Autowired
    UserMapper userMapper;

    public QuestionDTO question(Long id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        if(question == null){
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        User user = userMapper.selectByPrimaryKey(question.getCreatorId());
        BeanUtils.copyProperties(question,questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void incView(Long id) {
        questionExtMapper.incView(id);
    }
}
