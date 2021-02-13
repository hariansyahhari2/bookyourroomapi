package com.hariansyah.bookyourrooms.api.repositories;

import com.hariansyah.bookyourrooms.api.entities.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {

    @Query(value = "SELECT * FROM booking b " +
            "WHERE b.check_in_date <= :checkOutRequest " +
            "AND b.check_out_date >= :checkInRequest " +
            "AND b.status != 1 " +
            "AND b.room_id = :roomId",
            nativeQuery = true)
    List<Booking> findNumberOfBooked (@Param("checkInRequest") LocalDate checkInRequest, @Param("checkOutRequest") LocalDate checkOutRequest, @Param("roomId") Integer roomId);
}