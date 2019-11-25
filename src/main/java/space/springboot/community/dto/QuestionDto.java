package space.springboot.community.dto;

import lombok.Data;
import space.springboot.community.model.User;

@Data
public class QuestionDto {

    private Integer id;
    private String title;
    private String description;
    private Long gmtCreate;
    private Long gmtModified;
    private Integer creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String tag;
    private User user;
}
