package com.hariansyah.bookyourrooms.api.services.impl;

import com.hariansyah.bookyourrooms.api.entities.Room;
import com.hariansyah.bookyourrooms.api.services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class BookingServiceImpl extends CommonServiceImpl<Room, Integer> implements RoomService {

    @Autowired
    protected BookingServiceImpl(JpaRepository<Room, Integer> repository) {
        super(repository);
    }
}