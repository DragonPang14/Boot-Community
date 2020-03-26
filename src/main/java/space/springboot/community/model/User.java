package space.springboot.community.model;

import lombok.Data;

@Data
public class User {

    private Integer id;
    private String accountId;
    private String name;
    private String token;
    private Long gmtCreate;
    private Long gmtModified;
    private String avatarUrl;

//    增加注册
    private String userName;
    private String password;
    private String mobile;
    private String bio;  //简介
    private String mail;

}
