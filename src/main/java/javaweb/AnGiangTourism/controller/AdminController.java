package javaweb.AnGiangTourism.controller;

import jakarta.validation.Valid;
import javaweb.AnGiangTourism.dto.Auth.AdminLoginDto;
import javaweb.AnGiangTourism.service.AuthService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AdminController {

    AuthService authService;
    AuthenticationManager authenticationManager;

    @GetMapping("/admin/login")
    public String login(Model model){
        AdminLoginDto adminLoginDto = new AdminLoginDto();
        model.addAttribute("adminLoginView", adminLoginDto);
        return "/admin/login";
    }

    @PostMapping("/admin/logIn")
    public ModelAndView login(@ModelAttribute("adminLoginView") @Valid AdminLoginDto adminLoginDto
            , BindingResult bindingResult){
        ModelAndView modelAndView = new ModelAndView();

        // Kiểm tra xem email có tồn tại không
        if(!authService.existsByEmail(adminLoginDto.getEmail())){
            bindingResult.rejectValue("email", "error.email",
                    "Email account not found");
        }

        // Nếu có lỗi, trả về trang login với các lỗi hiển thị
        if(bindingResult.hasErrors()){
            bindingResult.getAllErrors().forEach(error -> {
                System.out.println("Lỗi: " + error.getDefaultMessage());
            });
            modelAndView.setViewName("/admin/login");
            return modelAndView;
        }
        try {
            // Tạo đối tượng UsernamePasswordAuthenticationToken để xác thực
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(adminLoginDto.getEmail(), adminLoginDto.getPassword());
            // Xác thực người dùng
            authenticationManager.authenticate(authToken);
            // Nếu thành công, chuyển hướng tới trang chính
            modelAndView.setViewName("redirect:/admin/home");
        }catch (AuthenticationException e){
            // Nếu xác thực thất bại, thêm lỗi và trả về trang login
            bindingResult.rejectValue("password", "error.password", "Invalid email or password");
            modelAndView.setViewName("/admin/login");
        }
        return modelAndView;
    }
}
