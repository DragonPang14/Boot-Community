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
    public ResultDto insertComment(Comment comment) {
        ResultDto resultDto = new ResultDto();
        if(!CommentTypeEnum.isExist(comment.getType())){
            resultDto.setCode(2001);
            resultDto.setMsg("回复类型错误");
            return resultDto;
        }
        if(comment.getType() == CommentTypeEnum.QUESTION_TYPE.getType()){
            Question question = questionMapper.findQuestionById(comment.getParentId());
            if (question != null){
                int commentId = commentMapper.insert(comment);
                questionMapper.incComment(comment.getParentId(),1);
                resultDto.setCode(100);
                resultDto.setObj(comment);
            }else {
                resultDto.setCode(2002);
                resultDto.setMsg("回复主题未找到");
                return resultDto;
            }
        }else {
            Comment dbComment = commentMapper.findCommentByCommentId(comment.getParentId());
            if (dbComment != null){
                int commentId = commentMapper.insert(comment);
                questionMapper.incComment(comment.getParentId(),1);
                resultDto.setCode(100);
                resultDto.setObj(comment);
            }else {
                resultDto.setCode(2002);
                resultDto.setMsg("回复评论未找到");
                return resultDto;
            }
        }
        return resultDto;
    }


    public List<CommentDto> QuestionComment(Integer id) {
        List<Comment> comments = commentMapper.findCommentById(id,CommentTypeEnum.QUESTION_TYPE.getType());
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
