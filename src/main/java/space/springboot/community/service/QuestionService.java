package space.springboot.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import space.springboot.community.dto.PaginationDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.Question;
import space.springboot.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;
    public PaginationDto<QuestionDto> getList(Integer page, Integer size) {
        Integer totalCount = questionMapper.totalCount();
        Integer totalPage = totalCount % 10 == 0?totalCount / 10:(totalCount / 10) + 1;
        if(page < 1){
            page = 1;
        }else if(page > totalPage){
            page = totalPage;
        }
//        偏移量
        Integer offset = size * ( page - 1);
        List<Question> questions = questionMapper.getList(offset,size);
        List<QuestionDto> questionDtos = new ArrayList<>();
        PaginationDto<QuestionDto> pagination= new PaginationDto<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pagination.setPageList(questionDtos);
        pagination.setPagination(totalPage,page);
        return pagination;
    }

    public PaginationDto<QuestionDto> getListByUserId(Integer userId, Integer page, Integer size) {
        Integer totalCount = questionMapper.userQuestionCount(userId);
        Integer totalPage = totalCount % 10 == 0?totalCount / 10:(totalCount / 10) + 1;
        if(page < 1){
            page = 1;
        }else if(page > totalPage){
            page = totalPage;
        }
//        偏移量
        Integer offset = size * ( page - 1);
        List<Question> questions = questionMapper.getListByUserId(userId,offset,size);
        List<QuestionDto> questionDtos = new ArrayList<>();
        PaginationDto<QuestionDto> pagination= new PaginationDto<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pagination.setPageList(questionDtos);
        pagination.setPagination(totalPage,page);
        return pagination;
    }

    public QuestionDto findQuestionById(String id) {
        Question question = questionMapper.findQuestionById(id);
        QuestionDto questionDto = new QuestionDto();
        if(question != null){
            User user = userMapper.findById(question.getCreator());
            BeanUtils.copyProperties(question,questionDto);
            questionDto.setUser(user);
        }
        return questionDto;
    }
}
