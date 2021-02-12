package com.hariansyah.bookyourrooms.api.repositories;

import com.hariansyah.bookyourrooms.api.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {
}