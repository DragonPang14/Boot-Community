package space.springboot.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.springboot.community.dto.UserDto;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.User;

import java.util.UUID;

@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * @desc 用户登录，更新或删除
     * @param user
     */
    public void insertOrUpdateUser(User user) {
        User dbUser = userMapper.findByAccountId(user.getAccountId());
        if(dbUser != null){
            dbUser.setAvatarUrl(user.getAvatarUrl());
            dbUser.setToken(user.getToken());
            dbUser.setName(user.getName());
            dbUser.setGmtModified(System.currentTimeMillis());
            dbUser.setUserName(user.getUserName());
            userMapper.updateUser(dbUser);
        }else {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insertUser(user);
        }
    }

    public User findByToken(String token) {
        User dbUser = userMapper.findByToken(token);
        return dbUser;
    }

    /**
     * @desc 注册
     * @param userDto
     * @return 是否注册成功
     */
    public int registered(UserDto userDto) {
        User user = new User();
        BeanUtils.copyProperties(userDto,user);
        user.setGmtCreate(System.currentTimeMillis());
        user.setGmtModified(user.getGmtCreate());
        user.setUserType(0);
        return userMapper.registered(user);
    }

    /**
     * @desc 查找重复用户名
     * @param userName
     * @return
     */
    public int findByUserName(String userName) {
        return userMapper.findByUserName(userName);
    }


    public User findById(Integer userId) {
        return userMapper.findById(userId);
    }

    public int findByMobile(String mobile) {
        return userMapper.findByMobile(mobile);
    }
}
