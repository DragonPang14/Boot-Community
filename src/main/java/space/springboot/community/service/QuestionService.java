package space.springboot.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import space.springboot.community.dto.PaginationDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.dto.TagDto;
import space.springboot.community.exception.CustomizeErrorCode;
import space.springboot.community.exception.CustomizeException;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.Question;
import space.springboot.community.model.Tag;
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
        Integer totalPage = totalCount % 10 == 0 ? totalCount / 10 : (totalCount / 10) + 1;
        if (page < 1) {
            page = 1;
        } else if (page > totalPage) {
            page = totalPage;
        }
//        偏移量
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.getList(offset, size);
        List<QuestionDto> questionDtos = new ArrayList<>();
        PaginationDto<QuestionDto> pagination = new PaginationDto<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pagination.setPageList(questionDtos);
        pagination.setPagination(totalPage, page);
        return pagination;
    }

    public PaginationDto<QuestionDto> getListByUserId(Integer userId, Integer page, Integer size) {
        Integer totalCount = questionMapper.userQuestionCount(userId);
        Integer totalPage = totalCount % 10 == 0 ? totalCount / 10 : (totalCount / 10) + 1;
        if (page < 1) {
            page = 1;
        } else if (page > totalPage) {
            page = totalPage;
        }
//        偏移量
        Integer offset = size * (page - 1);
        List<Question> questions = questionMapper.getListByUserId(userId, offset, size);
        List<QuestionDto> questionDtos = new ArrayList<>();
        PaginationDto<QuestionDto> pagination = new PaginationDto<>();
        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtos.add(questionDto);
        }
        pagination.setPageList(questionDtos);
        pagination.setPagination(totalPage, page);
        return pagination;
    }

    public QuestionDto findQuestionById(Integer id, int isView) {
        Question question = questionMapper.findQuestionById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        if (isView == 1) {
            questionMapper.incView(id,1);
        }
        QuestionDto questionDto = new QuestionDto();
        User user = userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUser(user);
        return questionDto;
    }

    public void createOrUpdate(QuestionDto questionDto) {
        if (StringUtils.isEmpty(questionDto.getId())) {
            Question question = new Question();
            BeanUtils.copyProperties(questionDto,question);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.createQuestion(question);
        } else {
            Question dbQuestion = questionMapper.findQuestionById(questionDto.getId());
            dbQuestion.setGmtModified(questionDto.getGmtCreate());
            dbQuestion.setTitle(questionDto.getTitle());
            dbQuestion.setDescription(questionDto.getDescription());
            questionMapper.updateQuestion(dbQuestion);
        }
    }

    /**
     * @desc save tag method
     * @param tagDto
     * @return
     */
    public int saveTag(TagDto tagDto) {
        Tag tag = new Tag();
        tag.setTagName(tagDto.getTagName());
        tag.setRemarks(tagDto.getRemarks());
        tag.setGmtCreate(System.currentTimeMillis());
        tag.setGmtModify(tag.getGmtCreate());
        int isSuccess = questionMapper.saveTag(tag);
        return isSuccess;
    }

    /**
     * @desc getTags method
     * @return
     */
    public List<TagDto> getTags() {
        List<Tag> tags = questionMapper.getTags();
        List<TagDto> tagDtos = new ArrayList<>();
        if (tags != null && tags.size() > 0 ){
            for (Tag tag : tags){
                TagDto tagDto = new TagDto();
                BeanUtils.copyProperties(tag,tagDto);
                tagDtos.add(tagDto);
            }
        }
        return tagDtos;
    }

    /**
     * @desc 根据标签名查找标签
     * @param tagName
     * @return
     */
    public int findTagByName(String tagName) {
        tagName = tagName.toLowerCase();
        int isExists = questionMapper.findTagByName(tagName);
        return isExists;
    }
}
