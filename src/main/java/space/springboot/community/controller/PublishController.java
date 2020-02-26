package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.dto.TagDto;
import space.springboot.community.enums.CustomizeStatusEnum;
import space.springboot.community.model.Question;
import space.springboot.community.model.User;
import space.springboot.community.service.QuestionService;
import space.springboot.community.service.UserService;

/**
 * @desc 发布页面
 */
@Controller
public class PublishController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable(name = "id") Integer id,
                               Model model){
        QuestionDto questionDto = questionService.findQuestionById(id, 1);
        if (questionDto != null){
            model.addAttribute("questionDto",questionDto);
        }
        return "publish";
    }

    @RequestMapping(value = "/doPublish",method = RequestMethod.POST)
    public @ResponseBody ResultDto doPublish(@RequestBody Question question,
                               @CookieValue(value = "token", required = false) String token) {
        try {
            if (token == null) {
                return new ResultDto(CustomizeStatusEnum.UNLOGIN_CODE);
            }
            User user = userService.findByToken(token);
            if (user != null) {
                question.setCreator(user.getId());
                question.setGmtCreate(System.currentTimeMillis());
                question.setGmtModified(question.getGmtCreate());
                questionService.createOrUpdate(question);
            }else{
                return new ResultDto(CustomizeStatusEnum.UNRECOGNIZED_USER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultDto(CustomizeStatusEnum.CODE_ERROR);
        }
        return new ResultDto(CustomizeStatusEnum.SUCCESS_CODE);
    }

    @RequestMapping(value = "/saveTag",method = RequestMethod.POST)
    public @ResponseBody ResultDto saveTag(@RequestBody TagDto tagDto){
        ResultDto<TagDto> resultDto;
        int tagId = questionService.saveTag(tagDto);
        if(tagId == 1){
            resultDto = new ResultDto<>(CustomizeStatusEnum.SUCCESS_CODE);
            resultDto.setObj(tagDto);
        }else {
            resultDto = new ResultDto<>(CustomizeStatusEnum.CODE_ERROR);
        }
        return resultDto;
    }
}
