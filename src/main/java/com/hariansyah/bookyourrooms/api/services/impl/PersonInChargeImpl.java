package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.ContactPerson;
import com.hariansyah.bookyourrooms.api.services.PersonInChargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonInChargeImpl extends CommonServiceImpl<ContactPerson, Integer> implements PersonInChargeService {

    @Autowired
    protected PersonInChargeImpl(JpaRepository<ContactPerson, Integer> repository) {
        super(repository);
    }
}
