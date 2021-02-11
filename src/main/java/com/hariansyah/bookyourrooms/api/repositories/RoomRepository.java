package com.hariansyah.bookyourrooms.api.repositories;

import com.hariansyah.bookyourrooms.api.entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Integer> {
}