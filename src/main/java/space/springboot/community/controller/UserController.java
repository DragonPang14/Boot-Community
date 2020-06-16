package space.springboot.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import space.springboot.community.dto.*;
import space.springboot.community.enums.CustomizeStatusEnum;
import space.springboot.community.exception.CustomizeErrorCode;
import space.springboot.community.exception.CustomizeException;
import space.springboot.community.model.User;
import space.springboot.community.service.UserService;
import space.springboot.community.utils.FastDfsUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private FastDfsUtils fastDfsUtils;

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
        if (userService.findByUserName(userDto.getUserName()) > 0){
            resultDto = new ResultDto<>(CustomizeStatusEnum.DUPLICATE_USER_NAME);
        }else if (userService.findByMobile(userDto.getMobile()) > 0){
            resultDto = new ResultDto<>(CustomizeStatusEnum.DUPLICATE_MOBILE);
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

    @PostMapping("/userLogin")
    public @ResponseBody ResultDto<UserDto> login(@RequestBody UserDto userDto,
                                                  HttpServletResponse response){
        ResultDto<UserDto> resultDto;
        if (userService.findByUserName(userDto.getUserName()) == 0){
            resultDto = new ResultDto<>(CustomizeStatusEnum.USERNAME_ERROR);
        }else {
            String token = userService.login(userDto);
            if (token !=null && !"".equals(token)){
                response.addCookie(new Cookie("token",token));
                resultDto = new ResultDto<>(CustomizeStatusEnum.SUCCESS_CODE);
            }else {
                resultDto = new ResultDto<>(CustomizeStatusEnum.PASSWORD_ERROR);
            }
        }
        return resultDto;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        request.getSession().invalidate();
        Cookie cookie = new Cookie("token",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }

    @PostMapping("/uploadAvatar")
    public @ResponseBody ResultDto uploadAvatar(AvatarDto avatarDto,
                                                @RequestParam(name = "avatar") MultipartFile avatar){
        try {
            String storePath = fastDfsUtils.uploadFile(avatar);
            avatarDto.setAvatarUrl(storePath);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultDto.errorOf(CustomizeStatusEnum.UPLOAD_ERROR);
        }
        return ResultDto.okOf(avatarDto);
    }

    @PostMapping("/modifyAvatar")
    public @ResponseBody ResultDto modifyAvatar(@RequestBody AvatarDto avatarDto){
        System.out.println("url:"+avatarDto.getAvatarUrl());
        int rows = userService.modifyAvatar(avatarDto);
        if (rows > 0){
            return new ResultDto(CustomizeStatusEnum.SUCCESS_CODE);
        }else {
            return new ResultDto(CustomizeStatusEnum.CODE_ERROR);
        }
    }

    @GetMapping("/notifications")
    public String notifications(@CookieValue(value = "token")String token,
                                @RequestParam(value = "page",required = false,defaultValue = "1")Integer page, Model model){
        User user = userService.findByToken(token);
        if (user == null){
            throw new CustomizeException(CustomizeErrorCode.USER_NOT_FOUND);
        }
        PaginationDto<NotificationDto> pagination = userService.notificationsList(user.getId(),page);
        model.addAttribute("pagination", pagination);
        return "notifications";
    }

}
