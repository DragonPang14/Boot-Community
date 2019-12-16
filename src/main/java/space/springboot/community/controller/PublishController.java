package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.model.Question;
import space.springboot.community.model.User;
import space.springboot.community.service.QuestionService;
import space.springboot.community.service.UserService;

@Controller
public class PublishController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable(name = "id") Integer id,
                               Model model){
        QuestionDto questionDto = questionService.findQuestionById(id, 1);
        if (questionDto != null){
            model.addAttribute("questionDto",questionDto);
        }
        return "publish";
    }

    @PostMapping("/doPublish")
    public String doPublish(Question question,
                            @CookieValue(value = "token", required = false) String token,
                            Model model) {
        try {
            if (token == null) {
                model.addAttribute("error", "用户不存在");
                return "publish";
            }
            User user = userService.findByToken(token);
            if (user != null) {
                question.setCreator(user.getId());
                question.setGmtCreate(System.currentTimeMillis());
                question.setGmtModified(question.getGmtCreate());
                questionService.createOrUpdate(question);
            }else{
                model.addAttribute("error", "用户不存在");
                return "publish";
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error","发布失败！");
            return "publish";
        }
        return "redirect:/";
    }
}
