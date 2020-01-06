package space.springboot.community.dto;

import lombok.Data;

@Data
public class InsertCommentDto {

    private Integer ParentId;
    private String content;
    private Integer type;
}
