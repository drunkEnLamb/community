package life.beyond.community.service;

import life.beyond.community.dto.PaginationDTO;
import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.mapper.QusetionMapper;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.Question;
import life.beyond.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QusetionMapper qusetionMapper;

    public PaginationDTO list(int userId, Integer page, Integer size) {
        if(page<1)  page = 1;
        Integer offset = size * (page - 1);

        List<Question> questionList = qusetionMapper.profileList(userId,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user = userMapper.findById(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);
        Integer totalCount = qusetionMapper.profileCount(userId);
        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }
}
