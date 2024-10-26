package javaweb.AnGiangTourism.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URLEncoder;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        // Truyền thông báo lỗi vào URL
        String errorMessage = "Invalid email or password"; // Lỗi mặc định

        if (exception instanceof UsernameNotFoundException) {
            errorMessage = "Email account not found";
        } else if (exception instanceof BadCredentialsException) {
            errorMessage = "Invalid password";
        }

        // Chuyển hướng với param error
        response.sendRedirect("/admin/login?error=" + URLEncoder.encode(errorMessage, "UTF-8"));
    }
}
