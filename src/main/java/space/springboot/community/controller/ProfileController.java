package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import space.springboot.community.dto.PaginationDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.model.User;
import space.springboot.community.service.QuestionService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ProfileController {


    @Autowired
    private QuestionService questionService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action,
                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                          @RequestParam(value = "size", defaultValue = "10") Integer size,
                          HttpServletRequest request,
                          Model model){
        User user = (User)request.getSession().getAttribute("user");
        if(user != null){
            if("question".equals(action)){
                PaginationDto<QuestionDto> pagination= questionService.getList(user.getId(),page, size,null);
                model.addAttribute("pagination", pagination);
                model.addAttribute("section","question");
                model.addAttribute("sectionName","我的发布");
            }else if("replies".equals(action)){
                model.addAttribute("section","replies");
                model.addAttribute("sectionName","最新回复");
            }
        }else{

        }
        return "profile";
    }
}
