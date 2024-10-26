package javaweb.AnGiangTourism.service;

import javaweb.AnGiangTourism.entity.Account;
import javaweb.AnGiangTourism.repository.AccountRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthService {

    AccountRepository accountRepository;

    public boolean existsByEmail(String email){
        return accountRepository.existsByEmail(email);
    }

}
