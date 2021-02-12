package com.hariansyah.bookyourrooms.api.models.entitymodels.elements;

import com.hariansyah.bookyourrooms.api.models.pagination.PageSearch;

import java.time.LocalTime;

public class HotelElement {

    private Integer id;

    private String name;

    private String about;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

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

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public LocalTime getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(LocalTime checkInTime) {
        this.checkInTime = checkInTime;
    }

    public LocalTime getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(LocalTime checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
