package space.springboot.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import space.springboot.community.model.User;

@Component
@Mapper
public interface UserMapper {

    @Insert("insert into user (name,account_id,token,gmt_create,gmt_modified,avatar_url) " +
            "values (#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified},#{avatarUrl})")
    void insertUser(User user);

    @Select("select * from user where token = #{token}")
    User findByToken(@Param("token") String token);

    @Select("select * from user where id = #{creator}")
    User findById(Integer creator);

    @Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl}")
    void updateUser(User dbUser);

    @Select("select * from user where account_id = #{token}")
    User findByAccountId(String token);
}
