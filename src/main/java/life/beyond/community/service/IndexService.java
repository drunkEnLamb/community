package life.beyond.community.service;

import life.beyond.community.dto.PaginationDTO;
import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.mapper.QuestionExtMapper;
import life.beyond.community.mapper.QuestionMapper;
import life.beyond.community.mapper.UserMapper;
import life.beyond.community.model.Question;
import life.beyond.community.model.QuestionExample;
import life.beyond.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class IndexService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    //根据token验证登陆状态
    public void index(HttpServletRequest request){

    }

    //展示问题列表
    public PaginationDTO list(String search,Integer page, Integer size){

        if(StringUtils.isNotBlank(search)){
            String[] s = StringUtils.split(search, " ");
            search = Arrays.stream(s).collect(Collectors.joining("|"));
        }

        if(page<1)  page = 1;
        Integer offset = size * (page - 1);
        QuestionExample questionExample = new QuestionExample();
        questionExample.setOrderByClause("gmt_create desc");
        //List<Question> questionList = questionMapper.selectByExampleWithBLOBsWithRowbounds(questionExample, new RowBounds(offset, size));
        //主页列表展示和搜索列表展示
        List<Question> questionList =questionExtMapper.selectBySearch(search,offset,size);
        List<QuestionDTO> questionDTOList = new ArrayList<>();

        PaginationDTO<QuestionDTO> paginationDTO = new PaginationDTO();
        for (Question question : questionList) {
            User user = userMapper.selectByPrimaryKey(question.getCreatorId());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setData(questionDTOList);
        //Integer totalCount = (int)questionMapper.countByExample(new QuestionExample());
        //总问题数和搜索问题数
        Integer totalCount = questionExtMapper.countBySearch(search);
        paginationDTO.setPagination(totalCount,page,size);

        return paginationDTO;
    }


}
