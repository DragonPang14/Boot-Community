package space.springboot.community.mapper;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import space.springboot.community.dto.PaginationDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.model.Question;
import space.springboot.community.model.Tag;

import java.util.List;

@Mapper
@Component
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator})")
    void createQuestion(Question question);

    @Select("select * from question limit #{offset},#{size}")
    List<Question> getList(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer totalCount();

    @Select("select * from question where creator = #{userId} limit #{offset},#{size}")
    List<Question> getListByUserId(@Param(value = "userId") Integer userId, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{userId}")
    Integer userQuestionCount(@Param(value = "userId") Integer userId);

    @Select("select * from question where id = #{id}")
    Question findQuestionById(@Param(value = "id") Integer id);

    @Update("update question set gmt_modified = #{gmtModified},title = #{title},description = #{description} where id = #{id}")
    void updateQuestion(Question dbQuestion);

    @Update("update question set view_count = view_count + #{i} where id = #{id}")
    void incView(@Param(value = "id") Integer id, @Param(value = "i") int i);

    @Update("update question set comment_count = comment_count + #{i} where id = #{id}")
    void incComment(@Param(value = "id") Integer parentId,@Param(value = "i") int i);

    @Insert("insert into tag (tag_name,remarks,gmt_create,gmt_modify) values (#{tagName},#{remarks},#{gmtCreate},#{gmtModify})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    int saveTag(Tag tag);

    @Select("select * from tag")
    List<Tag> getTags();

    @Select("select count(1) from tag where tag_name = #{tagName}")
    @Options(keyColumn = "count(1)")
    int findTagByName(String tagName);
}
