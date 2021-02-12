package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CompanyRequest {

    private Integer id;

    @NotBlank
    private String name;

    @NotNull
    private Integer cityId;

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

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}
