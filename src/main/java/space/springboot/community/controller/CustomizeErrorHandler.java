package space.springboot.community.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;

@Controller
public class CustomizeErrorHandler implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }
}
