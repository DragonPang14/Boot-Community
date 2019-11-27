package space.springboot.community.controller;

import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.springboot.community.dto.PaginationDto;
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
     * @param request
     * @return
     * @desc 打开首页，获取cookie自动登录
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @CookieValue(value = "token", required = false) String token,
                        @RequestParam(value = "page") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        Model model) {
        if (token != null) {
            User user = userMapper.findByToken(token);
            request.getSession().setAttribute("user", user);
        }
        PaginationDto<QuestionDto> pagination= questionService.getList(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
