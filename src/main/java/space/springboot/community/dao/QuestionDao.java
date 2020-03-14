package space.springboot.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import space.springboot.community.model.QuestionTags;

import java.util.List;

@Mapper
@Component
public interface QuestionDao {

    int saveQuestionTags(List<QuestionTags> questionTagsList);

}