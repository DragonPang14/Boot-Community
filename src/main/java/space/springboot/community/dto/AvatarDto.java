package space.springboot.community.dto;

import lombok.Data;

@Data
public class AvatarDto {
    private Integer id;
    private Integer userId;
    private String avatarUrl;
}
