package space.springboot.community.model;

import lombok.Data;

@Data
public class Comment {

    private Integer id;
    private Integer parentId;
    private String content;
    private int type;
    private Integer creator;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
}
