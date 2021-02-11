package com.hariansyah.bookyourrooms.api.models.entitymodels.responses;

import com.hariansyah.bookyourrooms.api.models.entitymodels.elements.RegionElement;

public class CityResponse {

    private Integer id;

    private String name;

    private RegionElement region;

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

    public RegionElement getRegion() {
        return region;
    }

    public void setRegion(RegionElement region) {
        this.region = region;
    }
}
