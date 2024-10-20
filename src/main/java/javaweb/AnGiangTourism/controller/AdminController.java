package javaweb.AnGiangTourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping({"/admin", "/admin/login"})
    public String adminHome(){
        return "/admin/home";
    }
}
