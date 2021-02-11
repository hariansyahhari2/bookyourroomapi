package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.Company;
import com.hariansyah.bookyourrooms.api.services.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CompanyImpl extends CommonServiceImpl<Company, Integer> implements CompanyService {

    @Autowired
    protected CompanyImpl(JpaRepository<Company, Integer> repository) {
        super(repository);
    }
}
