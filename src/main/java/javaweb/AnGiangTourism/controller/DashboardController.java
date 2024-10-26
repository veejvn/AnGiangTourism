package javaweb.AnGiangTourism.controller;

import jakarta.servlet.http.HttpSession;
import javaweb.AnGiangTourism.entity.Account;
import javaweb.AnGiangTourism.repository.AccountRepository;
import javaweb.AnGiangTourism.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final AccountRepository accountRepository;

    @GetMapping("admin/dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String dashboard(HttpSession session){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication !=null && authentication.isAuthenticated()){
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            String userId = userDetails.getId();
            Account account = accountRepository.findById(userId).orElse(null);
            session.setAttribute("account", account);
        }
        return "/admin/dashboard";
    }
}
