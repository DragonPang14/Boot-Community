package space.springboot.community.dto;

import lombok.Data;

@Data
public class UserDto {

    private Integer id;
    private String userName;
    private String name;
    private String password;
    private String mobile;
    private String bio;  //简介
    private String mail;
    private int userType;
    private String avatarUrl;

}
