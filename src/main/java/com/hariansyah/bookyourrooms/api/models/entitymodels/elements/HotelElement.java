package com.hariansyah.bookyourrooms.api.models.entitymodels.elements;

import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.entities.Company;

import java.sql.Time;

public class HotelElement {

    private Integer id;

    private String name;

    private String about;

    private Time checkInTime;

    private Time checkOutTime;

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

    public Time getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(Time checkInTime) {
        this.checkInTime = checkInTime;
    }

    public Time getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(Time checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
