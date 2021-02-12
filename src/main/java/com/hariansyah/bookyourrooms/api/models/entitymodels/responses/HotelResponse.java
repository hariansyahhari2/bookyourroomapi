package com.hariansyah.bookyourrooms.api.models.entitymodels.responses;

import com.hariansyah.bookyourrooms.api.entities.City;
import com.hariansyah.bookyourrooms.api.entities.Company;

import java.time.LocalTime;

public class HotelResponse {

    private Integer id;

    private String name;

    private String about;

    private LocalTime checkInTime;

    private LocalTime checkOutTime;

    private Company company;

    private City city;

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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }
}
