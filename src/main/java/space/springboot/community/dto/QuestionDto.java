package space.springboot.community.dto;

import lombok.Data;
import space.springboot.community.model.User;

import java.util.List;

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
    private List<TagDto> tagList;
    private User user;
}
