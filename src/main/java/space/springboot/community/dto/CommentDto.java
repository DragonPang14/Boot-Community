package space.springboot.community.dto;

import lombok.Data;

@Data
public class CommentDto {

    private Integer ParentId;
    private String content;
    private int type;
}
