package space.springboot.community.advice;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/error")
public class CustomizeErrorHandler implements ErrorController {

    @Override
    public String getErrorPath() {
        return "error";
    }

    @RequestMapping(  produces = {"text/html"})
    public ModelAndView errorHtml(HttpServletRequest request, Model model) {
        HttpStatus status = this.getStatus(request);
        if (status.is4xxClientError()){
            model.addAttribute("errorMessage","换个姿势试试？");

        }
        return new ModelAndView("error");
    }


    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer)request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (Exception var4) {
                var4.printStackTrace();
                return HttpStatus.INTERNAL_SERVER_ERROR;
            }
        }
    }
}
