package javaweb.AnGiangTourism.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping({"/admin"})
    public String adminHome(){
        return "/admin/home";
    }

    @GetMapping("/admin/login")
    public String logInAdmin(){
        return "/admin/login";
    }
}
