package space.springboot.community.model;

import lombok.Data;

@Data
public class QuestionTags {
    private Integer id;
    private Integer questionId;
    private Integer tagId;
    private Long gmtCreate;
    private int delFlag;
}
