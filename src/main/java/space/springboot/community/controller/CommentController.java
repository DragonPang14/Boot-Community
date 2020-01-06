package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import space.springboot.community.dto.InsertCommentDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.model.Comment;
import space.springboot.community.model.User;
import space.springboot.community.service.CommentService;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public @ResponseBody ResultDto comment(@RequestBody InsertCommentDto insertCommentDto,
                                           @SessionAttribute(name = "user",required = false) User user){
        if (user == null){
            return new ResultDto(1001,"用户未登录");
        }
        Comment comment = new Comment();
        comment.setParentId(insertCommentDto.getParentId());
        comment.setType(insertCommentDto.getType());
        comment.setContent(insertCommentDto.getContent());
        comment.setCreator(user.getId());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setLikeCount(0);
        ResultDto resultDto = commentService.insertComment(comment);
        return resultDto;
    }

}
