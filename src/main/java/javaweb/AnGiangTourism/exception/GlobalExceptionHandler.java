package javaweb.AnGiangTourism.exception;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsernameNotFoundException.class)
    public ModelAndView handleUsernameNotFoundException(UsernameNotFoundException e){
        ModelAndView modelAndView = new ModelAndView("/admin/login");
        modelAndView.addObject("email", e.getMessage());
        return modelAndView;
    }
}
