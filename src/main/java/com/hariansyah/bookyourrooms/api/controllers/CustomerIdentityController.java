package com.hariansyah.bookyourrooms.api.controllers;

import com.hariansyah.bookyourrooms.api.configs.jwt.JwtToken;
import com.hariansyah.bookyourrooms.api.entities.Account;
import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.exceptions.EntityNotFoundException;
import com.hariansyah.bookyourrooms.api.exceptions.ForeignKeyNotFoundException;
import com.hariansyah.bookyourrooms.api.models.ResponseMessage;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.CustomerIdentityRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.requests.CustomerIdentityWithAccountRequest;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CityResponse;
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
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/my-identity")
@RestController
public class CustomerIdentityController {

    @Autowired
    private CustomerIdentityService service;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtToken jwtTokenUtil;

    @GetMapping("/{id}")
    public ResponseMessage<CustomerIdentityResponse> findById(
            @PathVariable Integer id, HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Account account = accountRepository.findByUsername(username);
            CustomerIdentity entity = service.findById(id);
            if(entity != null) {
                if (entity.getAccount().getId().equals(account.getId())) {
                    CustomerIdentityResponse data = modelMapper.map(entity, CustomerIdentityResponse.class);
                    return ResponseMessage.success(data);
                }
            }
        }
        throw new EntityNotFoundException();
    }

    @PostMapping
    public ResponseMessage<CustomerIdentityResponse> add(
            @RequestBody @Valid CustomerIdentityRequest model,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Account account = accountRepository.findByUsername(username);

            CustomerIdentity entity = modelMapper.map(model, CustomerIdentity.class);
            entity.setAccount(account);
            entity = service.save(entity);

            CustomerIdentityResponse data = modelMapper.map(entity, CustomerIdentityResponse.class);
            return ResponseMessage.success(data);
        }
        throw new ForeignKeyNotFoundException();
    }

    @PutMapping("{id}")
    public ResponseMessage<CustomerIdentityResponse> edit(
            @PathVariable Integer id,
            @RequestBody @Valid CustomerIdentityRequest model,
            HttpServletRequest request
    ) {
        CustomerIdentity entity = service.findById(id);

        String token = request.getHeader("Authorization");
        if (entity != null && token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Account account = accountRepository.findByUsername(username);
            if (entity.getAccount().getId().equals(account.getId())) {
                modelMapper.map(model, entity);
                entity = service.save(entity);

                CustomerIdentityResponse data = modelMapper.map(entity, CustomerIdentityResponse.class);
                return ResponseMessage.success(data);
            }
        }
        throw new EntityNotFoundException();
    }

    @GetMapping("/all")
    public ResponseMessage<List<CustomerIdentityResponse>> findAll(
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
            String username = jwtTokenUtil.getUsernameFromToken(token);
            Account account = accountRepository.findByUsername(username);

            List<CustomerIdentity> entities = service.findAll().stream().filter(value -> value.getAccount().getId().equals(account.getId()))
                    .collect(Collectors.toList());

            List<CustomerIdentityResponse> data = entities.stream().map(e -> modelMapper.map(e, CustomerIdentityResponse.class))
                    .collect(Collectors.toList());

            return ResponseMessage.success(data);
        }
        throw new EntityNotFoundException();
    }
}