package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.configs.jwt.JwtToken;
import com.hariansyah.bookyourrooms.api.entities.Account;
import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.AccountRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.CustomerIdentityWithAccountRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.AccountResponse;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CustomerIdentityResponse;
import com.hariansyah.bookyourrooms.api.repositories.AccountRepository;
import com.hariansyah.bookyourrooms.api.services.CustomerIdentityService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.ValidationException;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/account")
@RestController
public class AccountController {

    @Autowired
    private AccountRepository repository;

    @Autowired
    private CustomerIdentityService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private JwtToken jwtTokenUtil;

    @PostMapping("/register")
    public ResponseMessage<CustomerIdentityResponse> addWithAccount(
            @RequestBody @Valid CustomerIdentityWithAccountRequest model
    ) {
        CustomerIdentity entity = modelMapper.map(model, CustomerIdentity.class);

        if (repository.existsByUsername(model.getAccount().getUsername())) {
            throw new ValidationException("Username already existed");
        }

        Account account = entity.getAccount();
        String hashPassword = new BCryptPasswordEncoder().encode(model.getAccount().getPassword());
        account.setPassword(hashPassword);

        repository.save(account);
        entity.setAccount(account);
        entity = service.save(entity);

        CustomerIdentityResponse data = modelMapper.map(entity, CustomerIdentityResponse.class);
        return ResponseMessage.success(data);
    }

    @PutMapping("/edit-account")
    public Boolean edit(@RequestBody AccountRequest moodel) throws NoSuchAlgorithmException {
        Account account = repository.findByUsername(moodel.getUsername());
        String password = moodel.getPassword();
        String encodedPassword = new BCryptPasswordEncoder().encode(password);

        account.setPassword(encodedPassword);
        repository.save(account);
        return true;
    }

    @GetMapping("/me")
    public ResponseMessage<AccountResponse> findById(
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            String username = jwtTokenUtil.getUsernameFromToken(token);

            Account account = repository.findByUsername(username);

            if(account != null) {
                AccountResponse data = modelMapper.map(account, AccountResponse.class);
                return ResponseMessage.success(data);
            }
        }
        throw new EntityNotFoundException();
    }
}
