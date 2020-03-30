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

    @Select("select * from user where token = #{token} order by gmt_create asc limit 0,1")
    User findByToken(String token);

    @Select("select * from user where id = #{creator}")
    User findById(Integer creator);

    @Update("update user set name = #{name},token = #{token},gmt_modified = #{gmtModified},avatar_url = #{avatarUrl},user_name = #{userName} " +
            "where account_id = #{accountId}")
    void updateUser(User dbUser);

    @Select("select * from user where account_id = #{token}")
    User findByAccountId(String token);

    @Select("select count(1) from user where user_name = #{userName} and del_flag = 0")
    @Options(keyColumn = "count(1)")
    int findByUserName(String userName);

    @Select("select count(1) from user where mobile = #{mobile} and del_flag = 0")
    @Options(keyColumn = "count(1)")
    int findByMobile(String mobile);

    @Insert("insert into user (name,gmt_create,gmt_modified,user_name,password,mobile,bio,mail) values" +
            "(#{name},#{gmtCreate},#{gmtModified},#{userName},#{password},#{mobile},#{bio},#{mail})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int registered(User user);


}
