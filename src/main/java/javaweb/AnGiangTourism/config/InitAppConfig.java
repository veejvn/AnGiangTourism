package javaweb.AnGiangTourism.config;

import javaweb.AnGiangTourism.entity.Account;
import javaweb.AnGiangTourism.repository.AccountRepository;
import javaweb.AnGiangTourism.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class InitAppConfig {
    private String ADMIN_EMAIL = "admin@gmail.com";
    private String ADMIN_PASSWORD = "admin@password";

    private final AccountRepository accountRepository;
    private final PasswordUtil passwordUtil;

    @Bean
    ApplicationRunner applicationRunner(){
        return args->{
            boolean isExistedAmin = accountRepository.existsByEmail(ADMIN_EMAIL);
            if (isExistedAmin) return;
            Account admin = Account.builder()
                    .email(ADMIN_EMAIL)
                    .name("Admin")
                    .password(passwordUtil.encodePassword(ADMIN_PASSWORD))
                    .build();
            accountRepository.save(admin);
        };
    }

}
