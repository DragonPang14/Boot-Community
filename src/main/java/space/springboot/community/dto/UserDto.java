package space.springboot.community.dto;

import lombok.Data;

@Data
public class UserDto {

    private String user_name;
    private String password;
    private String mobile;
    private String bio;  //简介
    private String mail;

}
