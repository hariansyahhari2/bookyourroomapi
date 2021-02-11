package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.Account;
import com.hariansyah.bookyourrooms.api.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends CommonServiceImpl<Account, Integer> implements AccountService {

    @Autowired
    protected AccountServiceImpl(JpaRepository<Account, Integer> repository) {
        super(repository);
    }
}
