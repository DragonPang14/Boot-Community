package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.Question;
import space.springboot.community.model.User;
import space.springboot.community.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;


    /**
     * @desc 打开首页，获取cookie自动登录
     * @param request
     * @return
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @CookieValue(value = "token" , required = false) String token,
                        Model model){
        if(token != null){
            User user = userMapper.findByToken(token);
            request.getSession().setAttribute("user",user);
        }
        List<QuestionDto> questions = questionService.getList();
        if(questions != null && questions.size() > 0){
            model.addAttribute("questions",questions);
        }
        return "index";
    }
}
