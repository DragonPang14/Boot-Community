package space.springboot.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import space.springboot.community.aspect.HyperLogInc;
import space.springboot.community.dto.CommentDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.enums.TargetTypeEnum;
import space.springboot.community.enums.NotificationTypeEnum;
import space.springboot.community.mapper.CommentMapper;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.Comment;
import space.springboot.community.model.Notification;
import space.springboot.community.model.Question;
import space.springboot.community.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @HyperLogInc(description = "评论数增加")
    @Transactional
    public ResultDto insertComment(Comment comment) {
        ResultDto resultDto = new ResultDto();
        Notification notification = null;
        boolean haveFlag = false;
        CommentDto commentDto = new CommentDto();
        if(comment.getType() == TargetTypeEnum.QUESTION_TYPE.getType() ){
            //判断是否找得到评论的主题
            Question questionById = questionMapper.findQuestionById(comment.getParentId());
            haveFlag = questionById != null;
            notification = this.createNotify(comment, questionById.getCreator());
        }else {
            //判断是否招的到回复的评论
            Comment commentByCommentId = commentMapper.findCommentByCommentId(comment.getParentId(), TargetTypeEnum.QUESTION_TYPE.getType());
            haveFlag =commentByCommentId != null;
            notification = this.createNotify(comment, commentByCommentId.getCreator());
        }
        if (haveFlag){
            commentMapper.insert(comment);
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMapper.findById(comment.getCreator()));
            resultDto.setCode(100);
            resultDto.setObj(commentDto);
            userMapper.insertNotification(notification);
        }else {
            if (comment.getType() == TargetTypeEnum.QUESTION_TYPE.getType()){
                resultDto.setCode(2002);
                resultDto.setMsg("回复主题未找到");
            }else {
                resultDto.setCode(2003);
                resultDto.setMsg("回复评论未找到");
            }
            return resultDto;
        }
        return resultDto;
    }

    //构建通知信息
    private Notification createNotify(Comment comment,Integer reveiveId){
        Notification notification = new Notification();
        notification.setReceiveId(reveiveId);
        notification.setSenderId(comment.getCreator());
        notification.setTargetId(comment.getParentId());
        notification.setTargetType(comment.getType());  //通知目标类型  --文章 ：评论
        notification.setAction(NotificationTypeEnum.COMMENT_ACTION.getCode());
        notification.setGmtCreate(System.currentTimeMillis());
        notification.setNotiContent(comment.getContent());  //评论内容
        return notification;
    }


    public List<CommentDto> getComments(Integer id, Integer commentType) {
        List<Comment> comments = commentMapper.findCommentById(id,commentType);
        if (comments.size() == 0){
            return new ArrayList<>();
        }
        //使用lamdba和stream来过滤评论或评论回复中重复的创建者id
        Set<Integer> commentCreatorId = comments.stream().map(comment -> comment.getCreator()).collect(Collectors.toSet());
        List<User> users = commentCreatorId.stream().map(integer -> {
            User user = userMapper.findById(integer);
            return user;
        }).collect(Collectors.toList());
        Map<Integer,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));
        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCreator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;
    }
}
