package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.Question;
import space.springboot.community.model.User;

@Controller
public class PublishController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @PostMapping("/doPublish")
    public String doPublish(Question question,
                            @CookieValue(value = "token", required = false) String token,
                            Model model) {
        System.out.println("title is :" + question.getTitle());

        try {
            if (token == null) {
                model.addAttribute("error", "用户不存在");
                return "publish";
            }
            User user = userMapper.findByToken(token);
            if (user != null) {
                question.setCreator(user.getId());
                question.setGmtCreate(System.currentTimeMillis());
                question.setGmtModified(question.getGmtCreate());
                questionMapper.createQuestion(question);
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
