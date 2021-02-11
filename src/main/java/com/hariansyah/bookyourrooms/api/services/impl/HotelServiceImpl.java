package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.Hotel;
import com.hariansyah.bookyourrooms.api.services.HotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class HotelServiceImpl extends CommonServiceImpl<Hotel, Integer> implements HotelService {

    @Autowired
    protected HotelServiceImpl(JpaRepository<Hotel, Integer> repository) {
        super(repository);
    }
}
