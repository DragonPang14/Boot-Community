package space.springboot.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import space.springboot.community.model.Question;

@Controller
public class PublishController {

    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/doPublish")
    public String doPublish(Question question,
                            @CookieValue(value = "token",required = false) String token){
        System.out.println("title is :" + question.getTitle());
        if(token == null){
            return "redirect:/";
        }
        System.out.println("token is :" + token);
        return "redirect:publish";
    }
}
