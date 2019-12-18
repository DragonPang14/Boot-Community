package space.springboot.community.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import space.springboot.community.dto.CommentDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.enums.CommentTypeEnum;
import space.springboot.community.mapper.CommentMapper;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.model.Comment;
import space.springboot.community.model.Question;

@Component
public class CommentService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private CommentMapper commentMapper;

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
            Comment dbComment = commentMapper.findCommentById(comment.getParentId());
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
}
