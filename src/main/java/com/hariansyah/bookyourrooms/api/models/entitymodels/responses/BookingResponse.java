package com.hariansyah.bookyourrooms.api.models.entitymodels.responses;

import com.hariansyah.bookyourrooms.api.enums.StatusEnum;

import java.time.LocalDateTime;

public class BookingResponse {

    private Integer id;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private Integer personCount;

    private AccountResponse bookedBy;

    private CustomerIdentityResponse guest;

    private RoomResponse room;

    private Integer subTotal;

    private StatusEnum status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public AccountResponse getBookedBy() {
        return bookedBy;
    }

    public void setBookedBy(AccountResponse bookedBy) {
        this.bookedBy = bookedBy;
    }

    public CustomerIdentityResponse getGuest() {
        return guest;
    }

    public void setGuest(CustomerIdentityResponse guest) {
        this.guest = guest;
    }

    public RoomResponse getRoom() {
        return room;
    }

    public void setRoom(RoomResponse room) {
        this.room = room;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
