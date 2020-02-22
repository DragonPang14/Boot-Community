package space.springboot.community.dto;

import lombok.Data;

@Data
public class InsertCommentDto {

    private Integer questionId;
    private Integer parentId;
    private String content;
    private Integer type;
}
