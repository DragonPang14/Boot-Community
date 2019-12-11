package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import space.springboot.community.dto.CommentDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.service.CommentService;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public @ResponseBody ResultDto comment(CommentDto commentDto){
        ResultDto resultDto = new ResultDto();
        commentService.insertComment(commentDto);
        return resultDto;
    }

}
