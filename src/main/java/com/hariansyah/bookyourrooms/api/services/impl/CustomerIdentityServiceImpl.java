package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.CustomerIdentity;
import com.hariansyah.bookyourrooms.api.services.CustomerIdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerIdentityServiceImpl extends CommonServiceImpl<CustomerIdentity, Integer> implements CustomerIdentityService {

    @Autowired
    protected CustomerIdentityServiceImpl(JpaRepository<CustomerIdentity, Integer> repository) {
        super(repository);
    }
}
