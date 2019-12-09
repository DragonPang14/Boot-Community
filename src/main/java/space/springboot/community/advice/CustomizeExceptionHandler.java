package space.springboot.community.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import space.springboot.community.exception.CustomizeException;

@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handleControllerException( Throwable e, Model model) {
        if (e instanceof CustomizeException){
            model.addAttribute("errorMessage",e.getMessage());
        }else{
            model.addAttribute("errorMessage","肯定是服务器的锅，要不等下试试？");
        }
        return new ModelAndView("error");
    }

}
