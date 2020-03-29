package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.dto.UserDto;
import space.springboot.community.enums.CustomizeStatusEnum;
import space.springboot.community.exception.CustomizeErrorCode;
import space.springboot.community.exception.CustomizeException;
import space.springboot.community.model.User;
import space.springboot.community.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/setting/{userId}")
    public String setting(@PathVariable(name = "userId") Integer userId, Model model){
        User user = userService.findById(userId);
        if (user != null){
            model.addAttribute("user",user);
        }else {
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
        }
        return "setting";
    }


    @PostMapping("/registered")
    public @ResponseBody
    ResultDto<UserDto> registered(@RequestBody UserDto userDto){
        ResultDto<UserDto> resultDto;
        int isExists = userService.findByUserName(userDto.getUserName());
        if (isExists > 0){
            resultDto = new ResultDto<>(CustomizeStatusEnum.DUPLICATE_USER);
        }else {
            int isSuccess = userService.registered(userDto);
            if (isSuccess == 1){
                resultDto = new ResultDto<>(CustomizeStatusEnum.SUCCESS_CODE);
            } else {
                resultDto = new ResultDto<>(CustomizeStatusEnum.CODE_ERROR);
            }
        }
        return resultDto;
    }

}
