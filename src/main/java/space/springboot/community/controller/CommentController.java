package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import space.springboot.community.dto.CommentDto;
import space.springboot.community.dto.InsertCommentDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.enums.CommentTypeEnum;
import space.springboot.community.model.Comment;
import space.springboot.community.model.User;
import space.springboot.community.service.CommentService;

import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * @desc 发送主题评论/评论回复
     * @param insertCommentDto
     * @param user
     * @return
     */
    @RequestMapping(value = "/sendComment",method = RequestMethod.POST)
    public @ResponseBody ResultDto sendComment(@RequestBody InsertCommentDto insertCommentDto,
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
        ResultDto resultDto = commentService.insertComment(insertCommentDto.getQuestionId(),comment);
        return resultDto;
    }

    /**
     * @desc 查询评论的回复列表
     * @param id
     * @return
     */
    @RequestMapping(value = "/getCommentsReply/{id}",method = RequestMethod.GET)
    public @ResponseBody ResultDto getCommentsReply(@PathVariable(name = "id")Integer id){
        List<CommentDto> commentDtos = commentService.getComments(id, CommentTypeEnum.COMMENT_TYPE.getType());
        return ResultDto.okOf(commentDtos);
    }

}
