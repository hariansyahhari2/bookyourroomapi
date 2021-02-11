package com.hariansyah.bookyourrooms.api.models.entitymodels.responses;

import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.CityElement;

import java.util.List;

public class RegionResponse {

    private Integer id;

    private String name;

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
}
