package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;


    /**
     * @desc 打开首页，获取cookie自动登录
     * @param request
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @CookieValue(value = "token" , required = false) String token){
        if(token != null){
            User user = userMapper.findByToken(token);
            request.getSession().setAttribute("user",user);
        }
        return "index";
    }
}
