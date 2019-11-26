package space.springboot.community.dto;

import lombok.Data;

@Data
public class GitHubUserDto {
    private String name;
    private long id;
    private String bio;
    private String avatarUrl;

}
