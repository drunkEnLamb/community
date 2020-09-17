package life.beyond.community.mapper;

import life.beyond.community.dto.QuestionDTO;
import life.beyond.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Mapper
public interface QusetionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator_id,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creatorId},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> list(@Param("offset") Integer offset,
                        @Param("size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question where creator_id=#{userId} limit #{offset},#{size}")
    List<Question> profileList(@Param("userId") int userId, @Param("offset") Integer offset, @Param("size") Integer size);

    @Select("select count(1) from question where creator_id=#{userId}")
    Integer profileCount(@Param("userId") int userId);

    @Select("select * from question where id=#{id}")
    Question getById(@Param("id") Integer id);
}
