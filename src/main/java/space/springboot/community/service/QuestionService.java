package space.springboot.community.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import space.springboot.community.aspect.HyperLogInc;
import space.springboot.community.dao.QuestionDao;
import space.springboot.community.dto.PaginationDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.dto.TagDto;
import space.springboot.community.exception.CustomizeErrorCode;
import space.springboot.community.exception.CustomizeException;
import space.springboot.community.mapper.QuestionMapper;
import space.springboot.community.mapper.UserMapper;
import space.springboot.community.model.Question;
import space.springboot.community.model.QuestionTags;
import space.springboot.community.model.Tag;
import space.springboot.community.model.User;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private QuestionDao questionDao;

    public PaginationDto<QuestionDto> getList(Integer userId, Integer page, Integer size,Integer tagId) {

        Integer totalCount = questionDao.totalCount(userId,tagId);
        Integer totalPage ;
        if (totalCount != 0){
            totalPage = totalCount % 10 == 0 ? totalCount / 10 : (totalCount / 10) + 1;
        }else {
            totalPage = 1;
        }
        if (page < 1) {
            page = 1;
        } else if (page > totalPage) {
            page = totalPage;
        }
//        偏移量
        Integer offset = size * (page - 1);
        PaginationDto<QuestionDto> pagination = new PaginationDto<>();
        List<QuestionDto> questionDtos = questionDao.getQuestionList(userId,offset,size,tagId);
        /*改为使用xml一次性查询出文章，和标签
        List<Question> questions = userId == null?
                questionMapper.getList(offset, size):questionMapper.getListByUserId(userId,offset,size);
        List<QuestionDto> questionDtos = new ArrayList<>();
        User loginUser = null;
        if (userId != null){
            loginUser = userMapper.findById(userId);
        }
        for (Question question : questions) {
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            if (loginUser != null){
                questionDto.setUser(loginUser);
            }else {
                User user = userMapper.findById(question.getCreator());
                questionDto.setUser(user);
            }
            questionDtos.add(questionDto);
        }
        */
        pagination.setPageList(questionDtos);
        pagination.setPagination(totalPage, page);
        return pagination;
    }

    @HyperLogInc(description = "增加阅读数")
    public QuestionDto findQuestionById(Integer id) {
        Question question = questionMapper.findQuestionById(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDto questionDto = new QuestionDto();
        User user = userMapper.findById(question.getCreator());
        BeanUtils.copyProperties(question, questionDto);
        questionDto.setUser(user);
        return questionDto;
    }


    public void createOrUpdate(QuestionDto questionDto, List<Integer> tagIdList) {
        Integer id;
        if (StringUtils.isEmpty(questionDto.getId())) {
            Question question = new Question();
            BeanUtils.copyProperties(questionDto,question);
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.createQuestion(question);
            id = question.getId();
        } else {
            Question dbQuestion = questionMapper.findQuestionById(questionDto.getId());
            dbQuestion.setGmtModified(questionDto.getGmtCreate());
            dbQuestion.setTitle(questionDto.getTitle());
            dbQuestion.setDescription(questionDto.getDescription());
            questionMapper.updateQuestion(dbQuestion);
            questionMapper.deleteQuestionTags(dbQuestion.getId());
            id = dbQuestion.getId();
        }
        List<QuestionTags> questionTagsList = new ArrayList<>();
        for (Integer tagId : tagIdList) {
            QuestionTags questionTags = new QuestionTags();
            questionTags.setQuestionId(id);
            questionTags.setTagId(tagId);
            questionTags.setGmtCreate(System.currentTimeMillis());
            questionTagsList.add(questionTags);
        }
        //保存问题与标签关系
        questionDao.saveQuestionTags(questionTagsList);
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
