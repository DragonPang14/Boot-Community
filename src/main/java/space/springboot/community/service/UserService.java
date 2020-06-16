package space.springboot.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import space.springboot.community.dao.UserDao;
import space.springboot.community.dto.*;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.User;
import space.springboot.community.utils.CommonUtils;

import java.util.List;
import java.util.UUID;

@Component
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Value("${DEFAULT_AVATAR}")
    private String DEFAULT_AVATAR;

    @Autowired
    private UserDao userDao;
    /**
     * @desc 用户登录，更新
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
        user.setAvatarUrl(DEFAULT_AVATAR);
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

    public String login(UserDto userDto) {
        User dbUser = userMapper.verifyUser(userDto);
        if (dbUser != null){
            dbUser.setToken(UUID.randomUUID().toString());
            userMapper.updateUser(dbUser);
            return dbUser.getToken();
        }
        return null;
    }

    public int modifyAvatar(AvatarDto avatarDto) {
        return userMapper.modifyAvatar(avatarDto);
    }


    public PaginationDto<NotificationDto> notificationsList(Integer userId,Integer page) {
        Integer totalCount = userDao.totalNotification(userId);
        //计算总页数
        Integer totalPage = CommonUtils.calculateTotalPage(totalCount);
        //计算偏移量
        Integer offset = CommonUtils.calculatePageOffset(totalPage, page, 10);
        PaginationDto<NotificationDto> pagination = new PaginationDto<>();
        List<NotificationDto> notificationsDto = userDao.getNotifications(userId,offset,10);
        pagination.setPageList(notificationsDto);
        pagination.setPagination(totalPage, page);
        return pagination;
    }
}
