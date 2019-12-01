package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import space.springboot.community.dto.PaginationDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;


    /**
     * @param request
     * @return
     * @desc 打开首页，获取cookie自动登录
     */
    @GetMapping("/")
    public String index(HttpServletRequest request,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        Model model) {
        PaginationDto<QuestionDto> pagination= questionService.getList(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}
