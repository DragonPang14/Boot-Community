package space.springboot.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import space.springboot.community.model.Comment;

@Mapper
@Component
public interface CommentMapper {


    @Insert("insert into comment (parent_id,content,type,creator,like_count,gmt_create,gmt_modified) values " +
            "(#{parentId},#{content},#{type},#{creator},#{likeCount},#{gmtCreate},#{gmtModified})")
    @Options(keyColumn = "id",useGeneratedKeys = true,keyProperty = "id")
    int insert(Comment comment);

    @Select("select * from comment where id = #{parentId}")
    Comment findCommentById(@Param(value = "parentId") Integer parentId);
}
