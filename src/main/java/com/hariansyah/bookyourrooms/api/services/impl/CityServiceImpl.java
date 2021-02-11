package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.services.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class CityServiceImpl extends CommonServiceImpl<City, Integer> implements CityService {

    @Autowired
    protected CityServiceImpl(JpaRepository<City, Integer> repository) {
        super(repository);
    }
}
