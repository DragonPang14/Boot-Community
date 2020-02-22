package space.springboot.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import space.springboot.community.dto.CommentDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.enums.CommentTypeEnum;
import space.springboot.community.mapper.CommentMapper;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.Comment;
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

    @Transactional
    public ResultDto insertComment(Integer questionId, Comment comment) {
        ResultDto resultDto = new ResultDto();
        if(!CommentTypeEnum.isExist(comment.getType())){
            resultDto.setCode(2001);
            resultDto.setMsg("回复类型错误");
            return resultDto;
        }
        boolean haveFlag = false;
        CommentDto commentDto = new CommentDto();
        if(comment.getType() == CommentTypeEnum.QUESTION_TYPE.getType() ){
            //判断是否找得到评论的主题
            haveFlag = questionMapper.findQuestionById(comment.getParentId()) != null;
        }else {
            //判断是否招的到回复的评论
            haveFlag = commentMapper.findCommentByCommentId(comment.getParentId(),CommentTypeEnum.QUESTION_TYPE.getType()) != null;
        }
        if (haveFlag){
            int commentId = commentMapper.insert(comment);
            questionMapper.incComment(questionId,1);
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMapper.findById(comment.getCreator()));
            resultDto.setCode(100);
            resultDto.setObj(commentDto);
        }else {
            if (comment.getType() == CommentTypeEnum.QUESTION_TYPE.getType()){
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
        Map<Integer,User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(),user -> user));
        List<CommentDto> commentDtos = comments.stream().map(comment -> {
            CommentDto commentDto = new CommentDto();
            BeanUtils.copyProperties(comment,commentDto);
            commentDto.setUser(userMap.get(comment.getCreator()));
            return commentDto;
        }).collect(Collectors.toList());
        return commentDtos;
    }
}
