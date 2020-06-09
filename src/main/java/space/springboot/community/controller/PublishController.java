package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.springboot.community.dto.ImageResultDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.dto.TagDto;
import space.springboot.community.enums.CustomizeStatusEnum;
import space.springboot.community.model.Question;
import space.springboot.community.model.User;
import space.springboot.community.service.QuestionService;
import space.springboot.community.service.UserService;
import space.springboot.community.utils.FastDfsUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @desc 发布页面
 */
@Controller
public class PublishController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private FastDfsUtils fastDfsUtils;

    @GetMapping("/publish")
    public String publish() {
        return "publish";
    }

    @GetMapping("/publish/{id}")
    public String editQuestion(@PathVariable(name = "id") Integer id,
                               Model model){
        QuestionDto questionDto = questionService.findQuestionById(id);
        if (questionDto != null){
            model.addAttribute("questionDto",questionDto);
        }
        return "publish";
    }

    @RequestMapping(value = "/doPublish",method = RequestMethod.POST)
    public @ResponseBody ResultDto doPublish(@RequestBody QuestionDto questionDto,
                               @CookieValue(value = "token", required = false) String token) {
        try {
            if (token == null) {
                return new ResultDto(CustomizeStatusEnum.UNLOGIN_CODE);
            }
            User user = userService.findByToken(token);
            String tag = questionDto.getTag().substring(0,questionDto.getTag().length() - 1);
            String[] tagIdStr = tag.split(",");
            List<Integer> tagIdList = new ArrayList<>();
            for (String s : tagIdStr) {
                Integer tagId = Integer.valueOf(s);
                tagIdList.add(tagId);
            }
            if (user != null) {
                questionDto.setCreator(user.getId());
                questionService.createOrUpdate(questionDto,tagIdList);
            }else{
                return new ResultDto(CustomizeStatusEnum.UNRECOGNIZED_USER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResultDto(CustomizeStatusEnum.CODE_ERROR);
        }
        return new ResultDto(CustomizeStatusEnum.SUCCESS_CODE);
    }

    /**
     * @desc md上传图片，返回图片地址
     * @param image
     * @return
     */
    @PostMapping("/uploadImage")
    public @ResponseBody
    ImageResultDto uploadImage(@RequestParam(value = "editormd-image-file", required = true) MultipartFile image){
        ImageResultDto imageResultDto = new ImageResultDto();
        try {
            String imagePath = fastDfsUtils.uploadFile(image);
            imageResultDto.setSuccess(1);
            imageResultDto.setMsg("上传成功!");
            imageResultDto.setUrl(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
            imageResultDto.setSuccess(0);
            imageResultDto.setMsg("上传失败！");
        }
        return imageResultDto;
    }

    /**
     * @desc 保存标签
     * @param tagDto
     * @return
     */
    @RequestMapping(value = "/saveTag",method = RequestMethod.POST)
    public @ResponseBody ResultDto saveTag(@RequestBody TagDto tagDto){
        ResultDto<TagDto> resultDto;
        int isExists = questionService.findTagByName(tagDto.getTagName());
        if (isExists == 1){
            resultDto = new ResultDto<>(CustomizeStatusEnum.TAG_EXISTS);
        }else {
            int tagId = questionService.saveTag(tagDto);
            resultDto = tagId == 1?new ResultDto<>(CustomizeStatusEnum.SUCCESS_CODE):
                    new ResultDto<>(CustomizeStatusEnum.CODE_ERROR);
        }
        return resultDto;
    }

    @RequestMapping(value = "/getTags",method = RequestMethod.POST)
    public @ResponseBody ResultDto getTags(){
        ResultDto<List<TagDto>> resultDto;
        List<TagDto> tagDtos;
        try {
            tagDtos = questionService.getTags();
        } catch (Exception e) {
            e.printStackTrace();
            resultDto = new ResultDto<>(CustomizeStatusEnum.CODE_ERROR);
            return resultDto;
        }
        resultDto = new ResultDto<>(CustomizeStatusEnum.SUCCESS_CODE);
        resultDto.setObj(tagDtos);
        return resultDto;
    }
}
