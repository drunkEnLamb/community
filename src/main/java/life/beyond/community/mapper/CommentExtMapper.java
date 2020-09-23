package life.beyond.community.mapper;

import life.beyond.community.model.Comment;
import life.beyond.community.model.CommentExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
   int incCommentCount(Long id);
}