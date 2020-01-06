package space.springboot.community.dto;

import lombok.Data;
import space.springboot.community.model.User;

@Data
public class CommentDto {

    private Integer id;
    private Integer parentId;
    private String content;
    private int type;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private User user;
}
