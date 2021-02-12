package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.Booking;
import com.hariansyah.bookyourrooms.api.services.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl extends CommonServiceImpl<Booking, Integer> implements BookingService {

    @Autowired
    protected BookingServiceImpl(JpaRepository<Booking, Integer> repository) {
        super(repository);
    }
}