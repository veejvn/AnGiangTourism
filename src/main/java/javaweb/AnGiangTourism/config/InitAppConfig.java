package javaweb.AnGiangTourism.config;

import javaweb.AnGiangTourism.entity.Account;
import javaweb.AnGiangTourism.enums.Role;
import javaweb.AnGiangTourism.repository.AccountRepository;
import javaweb.AnGiangTourism.util.PasswordUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.EnumSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
public class InitAppConfig {
    private String ADMIN_EMAIL = "admin@gmail.com";
    private String ADMIN_PASSWORD = "admin@password";

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner applicationRunner(){
        return args -> {
            boolean isExistedAdmin = accountRepository.existsByEmail(ADMIN_EMAIL);
            if (isExistedAdmin) return;

            Set<Role> roles = EnumSet.noneOf(Role.class);
            roles.add(Role.ADMIN);

            Account admin = Account.builder()
                    .email(ADMIN_EMAIL)
                    .name("Admin")
                    .roles(roles)
                    .password(passwordEncoder.encode(ADMIN_PASSWORD))
                    .build();
            accountRepository.save(admin);
        };
    }
}
