package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

import com.hariansyah.bookyourrooms.api.enums.StatusEnum;

import java.time.LocalDate;

public class BookingRequest {

    private Integer id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

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

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
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
