package javaweb.AnGiangTourism.service;

import javaweb.AnGiangTourism.entity.Account;
import javaweb.AnGiangTourism.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {
    AccountRepository accountRepository;

    PasswordEncoder passwordEncoder;

    public boolean existsByEmail(String email){
        return accountRepository.existsByEmail(email);
    }

    public Account findByEmail(String email){
        return accountRepository.findByEmail(email);
    }
}
