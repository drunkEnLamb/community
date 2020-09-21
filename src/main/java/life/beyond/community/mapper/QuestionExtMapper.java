package life.beyond.community.mapper;

import life.beyond.community.model.Question;
import life.beyond.community.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {

    int incView(Long id);
    int incCommentCount(Long id);
}