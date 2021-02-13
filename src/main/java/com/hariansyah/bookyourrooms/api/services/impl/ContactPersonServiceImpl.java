package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.ContactPerson;
import com.hariansyah.bookyourrooms.api.services.ContactPersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class ContactPersonServiceImpl extends CommonServiceImpl<ContactPerson, Integer> implements ContactPersonService {

    @Autowired
    protected ContactPersonServiceImpl(JpaRepository<ContactPerson, Integer> repository) {
        super(repository);
    }
}
