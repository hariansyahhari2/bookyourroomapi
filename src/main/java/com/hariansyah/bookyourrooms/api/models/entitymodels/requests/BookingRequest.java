package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

import com.hariansyah.bookyourrooms.api.enums.StatusEnum;

import java.time.LocalDateTime;

public class BookingRequest {

    private Integer id;

    private String name;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private Integer personCount;

    private Integer bookedById;

    private Integer guestId;

    private Integer roomId;

    private StatusEnum status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDateTime checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDateTime getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDateTime checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Integer getPersonCount() {
        return personCount;
    }

    public void setPersonCount(Integer personCount) {
        this.personCount = personCount;
    }

    public Integer getBookedById() {
        return bookedById;
    }

    public void setBookedById(Integer bookedById) {
        this.bookedById = bookedById;
    }

    public Integer getGuestId() {
        return guestId;
    }

    public void setGuestId(Integer guestId) {
        this.guestId = guestId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
