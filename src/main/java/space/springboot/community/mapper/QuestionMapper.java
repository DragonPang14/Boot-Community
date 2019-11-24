package space.springboot.community.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import space.springboot.community.model.Question;

@Mapper
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) " +
            "values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void createQuestion(Question question);
}
