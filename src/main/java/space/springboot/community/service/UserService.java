package space.springboot.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.User;

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
}
