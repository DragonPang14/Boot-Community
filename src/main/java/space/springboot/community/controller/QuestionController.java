package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import space.springboot.community.dto.CommentDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.enums.CommentTypeEnum;
import space.springboot.community.service.CommentService;
import space.springboot.community.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable(name = "id") Integer id,
                           Model model){
        QuestionDto questionDto = questionService.findQuestionById(id,1);
        List<CommentDto> commentDtoList = commentService.getComments(id, CommentTypeEnum.QUESTION_TYPE.getType());
        model.addAttribute("questionDto",questionDto);
        model.addAttribute("commentDtoList",commentDtoList);
        return "question";
    }
}
