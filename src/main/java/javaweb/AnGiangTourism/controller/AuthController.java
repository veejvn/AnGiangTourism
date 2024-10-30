package javaweb.AnGiangTourism.controller;

import jakarta.servlet.http.HttpSession;
import javaweb.AnGiangTourism.dto.auth.AdminLoginRequest;
import javaweb.AnGiangTourism.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/admin/login")
    public String login(Model model, Authentication authentication, HttpSession session){
        if(authentication !=null && session !=null){
            return "redirect:/admin/dashboard";
        }
        model.addAttribute("adminLoginView", new AdminLoginRequest());
        return "admin/login";
    }

}
