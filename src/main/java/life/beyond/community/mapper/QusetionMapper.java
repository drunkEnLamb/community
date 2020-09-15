package life.beyond.community.mapper;

import life.beyond.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
public interface QusetionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator_id,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creatorId},#{tag})")
    void create(Question question);

    @Select("select * from question")
    List<Question> list();
}
