package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

import com.hariansyah.bookyourrooms.api.enums.StatusEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class BookingRequest {

    private Integer id;

    @NotBlank
    private LocalDate checkInDate;

    @NotBlank
    private LocalDate checkOutDate;

    @NotNull
    private Integer personCount;

    @NotNull
    private Integer bookedById;

    @NotNull
    private Integer guestId;

    @NotNull
    private Integer roomId;

    @NotBlank
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
