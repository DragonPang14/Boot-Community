package space.springboot.community.dao;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import space.springboot.community.dto.NotificationDto;

import java.util.List;

@Mapper
@Component
public interface UserDao {

    Integer totalNotification(Integer userId,Integer status);

    List<NotificationDto> getNotifications(Integer userId, Integer offset, int size);
}
