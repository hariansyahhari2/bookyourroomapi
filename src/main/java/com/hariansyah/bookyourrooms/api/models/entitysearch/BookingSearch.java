package com.hariansyah.bookyourrooms.api.models.entitysearch;

import com.hariansyah.bookyourrooms.api.enums.StatusEnum;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.AccountResponse;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CustomerIdentityResponse;
import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.RoomResponse;
import com.hariansyah.bookyourrooms.api.models.pagination.PageSearch;

import java.time.LocalDateTime;

import static com.hariansyah.bookyourrooms.api.enums.StatusEnum.CONFIRMED;

public class BookingSearch extends PageSearch {

    private Integer id;

    private String name;

    private LocalDateTime checkInDate;

    private LocalDateTime checkOutDate;

    private Integer personCount;

    private Integer subTotal;

    private StatusEnum status = CONFIRMED;

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
