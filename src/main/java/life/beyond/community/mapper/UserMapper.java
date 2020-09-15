package life.beyond.community.mapper;

import life.beyond.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,bio,password,avatar_url) values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{bio},#{password},#{avatarUrl})")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId(@Param("accountId") long accountId);

    @Select("select * from user where name=#{name} and password=#{password}")
    User findByNameAndPwd(@Param("name") String name,
                          @Param("password") String password);

    @Select("select * from user where id=#{id}")
    User findById(@Param("id") Integer id);
}
