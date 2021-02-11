package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.entities.Account;
import com.hariansyah.bookyourrooms.api.repositories.AccountRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

@RequestMapping("/account")
@RestController
public class AccountController {
    private final AccountRepository repository;

    public AccountController(AccountRepository accountRepository) {
        this.repository = accountRepository;
    }

    @PostMapping("/register")
    public Boolean create(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
        String username = body.get("username");
        if (repository.existsByUsername(username)) {
            throw new ValidationException("Username already existed");
        }

        String password = body.get("password");
        String encodedPassword = new BCryptPasswordEncoder().encode(password);
        String email = body.get("email");
        repository.save(new Account(username, encodedPassword, email));
        return true;
    }

    @PutMapping("/edit-account")
    public Boolean edit(@RequestBody Map<String, String> body) throws NoSuchAlgorithmException {
        Account account = repository.findByUsername(body.get("username"));
        String password = body.get("password");
        String encodedPassword = new BCryptPasswordEncoder().encode(password);

        account.setPassword(encodedPassword);
        repository.save(account);
        return true;
    }
}
