package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.PersonInCharge;
import com.hariansyah.bookyourrooms.api.services.PersonInChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonInChargeImpl extends CommonServiceImpl<PersonInCharge, Integer> implements PersonInChargeService {

    @Autowired
    protected PersonInChargeImpl(JpaRepository<PersonInCharge, Integer> repository) {
        super(repository);
    }
}
