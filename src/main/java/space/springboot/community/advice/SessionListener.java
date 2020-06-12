package space.springboot.community.advice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import space.springboot.community.model.User;
import space.springboot.community.utils.DateUtils;
import space.springboot.community.utils.RedisUtils;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Date;

@WebListener
public class SessionListener implements HttpSessionListener, HttpSessionAttributeListener {

    @Value("${ACTIVE_USER}")
    private String ACTIVE_USER;

    @Autowired
    private RedisUtils redisUtils;

    @Override
    public void sessionDestroyed(HttpSessionEvent se){
        User user = (User)se.getSession().getAttribute("user");
        System.out.println("user destroyed:"+user.toString());
        System.out.println("createTime:"+DateUtils.formatDateTime(new Date(se.getSession().getCreationTime())));
        System.out.println("lastAccessTime:"+DateUtils.formatDateTime(new Date(se.getSession().getLastAccessedTime())));
        System.out.println("destroyTime:"+DateUtils.getDateTime());
        redisUtils.setBit(ACTIVE_USER,user.getId(),false);
    }

    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {
        User user = (User)se.getSession().getAttribute("user");
        redisUtils.setBit(ACTIVE_USER,user.getId(),true);
    }
}
