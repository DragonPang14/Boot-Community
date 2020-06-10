package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import space.springboot.community.dto.PaginationDto;
import space.springboot.community.dto.QuestionDto;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.enums.CustomizeStatusEnum;
import space.springboot.community.model.Question;
import space.springboot.community.service.QuestionService;
import space.springboot.community.utils.DateUtils;
import space.springboot.community.utils.RedisUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private RedisUtils redisUtils;

    @Value("${RANK_KEY}")
    private String RANK_KEY;

    /**
     * @return
     * @desc 打开首页，获取cookie自动登录
     */
    @GetMapping("/")
    public String index(@RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                        @RequestParam(value = "tag", required = false) Integer tagId,
                        Model model) {
        PaginationDto<QuestionDto> pagination= questionService.getList(null,page, size,tagId);
        model.addAttribute("pagination", pagination);
        return "index";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    /**
     * @desc 侧边栏热门文章
     * @return
     */
    @PostMapping("/getHotRank")
    public @ResponseBody
    ResultDto<List<QuestionDto>> hotRank(){
        ResultDto<List<QuestionDto>> resultDto ;
        String redisZKey = RANK_KEY + ":" + DateUtils.getDate();
        List<QuestionDto> questionDtos = null;
        try {
            //从redis中获取近期热门文章id
            Set<String> questionIds = redisUtils.zRank(redisZKey, 0, 10);
            questionDtos = questionIds.stream().map( questionId -> {
                QuestionDto questionDto = questionService.findQuestionById(Integer.parseInt(questionId));
                return questionDto;
            }).collect(Collectors.toList());
            resultDto = new ResultDto<>(CustomizeStatusEnum.SUCCESS_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            resultDto = new ResultDto<>(CustomizeStatusEnum.HOT_RANK_ERROR);
        }
        resultDto.setObj(questionDtos);
        return resultDto;
    }
}
