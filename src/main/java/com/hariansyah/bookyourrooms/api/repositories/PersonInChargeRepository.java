package com.hariansyah.bookyourrooms.api.repositories;

import com.hariansyah.bookyourrooms.api.entities.PersonInCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonInChargeRepository extends JpaRepository<PersonInCharge, Integer> {
}