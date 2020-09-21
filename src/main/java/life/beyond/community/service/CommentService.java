package life.beyond.community.service;

import life.beyond.community.dto.CommentDTO;
import life.beyond.community.enums.CommentTypeEnum;
import life.beyond.community.exception.CustomizeErrorCode;
import life.beyond.community.exception.CustomizeException;
import life.beyond.community.mapper.CommentMapper;
import life.beyond.community.mapper.QuestionExtMapper;
import life.beyond.community.mapper.QuestionMapper;
import life.beyond.community.model.Comment;
import life.beyond.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentMapper commentMapper;

    @Autowired
    QuestionMapper questionMapper;

    @Autowired
    QuestionExtMapper questionExtMapper;

    public void insert(Comment comment){
        if(comment.getParentId() == null|| comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){

            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            if(dbComment == null){
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);
        }else {
            //回复问题
            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(question == null){
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);
            questionExtMapper.incCommentCount(comment.getParentId());
        }

    }
}
