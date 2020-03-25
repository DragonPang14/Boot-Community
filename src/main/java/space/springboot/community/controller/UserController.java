package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import space.springboot.community.dto.ResultDto;
import space.springboot.community.dto.UserDto;
import space.springboot.community.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/registered")
    public @ResponseBody
    ResultDto<UserDto> registered(@RequestBody UserDto userDto){
        ResultDto<UserDto> resultDto;
        int isSuccess = userService.registered(userDto);
        return resultDto;
    }

}
