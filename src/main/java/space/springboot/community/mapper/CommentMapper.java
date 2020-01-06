package space.springboot.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import space.springboot.community.dto.CommentDto;
import space.springboot.community.model.Comment;

import java.util.List;

@Mapper
@Component
public interface CommentMapper {


    @Insert("insert into comment (parent_id,content,type,creator,like_count,gmt_create,gmt_modified) values " +
            "(#{parentId},#{content},#{type},#{creator},#{likeCount},#{gmtCreate},#{gmtModified})")
    @Options(keyColumn = "id",useGeneratedKeys = true,keyProperty = "id")
    int insert(Comment comment);

    @Select("select * from comment where id = #{parentId} and type = #{type}")
    List<Comment> findCommentById(@Param(value = "parentId") Integer parentId, @Param(value = "type") Integer type);

    @Select("select * from comment where id = #{parentId} and type = 2")
    Comment findCommentByCommentId(@Param(value = "parentId") Integer parentId);
}
