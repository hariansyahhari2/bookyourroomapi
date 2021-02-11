package com.hariansyah.bookyourrooms.api.repositories;

import com.hariansyah.bookyourrooms.api.entities.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Integer> {
}