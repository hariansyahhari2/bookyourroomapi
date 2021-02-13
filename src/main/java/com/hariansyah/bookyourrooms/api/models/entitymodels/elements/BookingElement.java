package com.hariansyah.bookyourrooms.api.models.entitymodels.elements;

import com.hariansyah.bookyourrooms.api.enums.StatusEnum;

import java.time.LocalDate;

import static com.hariansyah.bookyourrooms.api.enums.StatusEnum.CONFIRMED;

public class BookingElement {

    private Integer id;

    private LocalDate checkInDate;

    private LocalDate checkOutDate;

    private Integer personCount;

    private Integer subTotal;

    private Long numberOfNight;

    private Long roomCount;

    private StatusEnum status = CONFIRMED;

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

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public Long getNumberOfNight() {
        return numberOfNight;
    }

    public void setNumberOfNight(Long numberOfNight) {
        this.numberOfNight = numberOfNight;
    }

    public Long getRoomCount() {
        return roomCount;
    }

    public void setRoomCount(Long roomCount) {
        this.roomCount = roomCount;
    }

    public StatusEnum getStatus() {
        return status;
    }

    public void setStatus(StatusEnum status) {
        this.status = status;
    }
}
