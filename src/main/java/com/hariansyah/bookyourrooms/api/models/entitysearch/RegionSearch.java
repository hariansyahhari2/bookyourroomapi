package com.hariansyah.bookyourrooms.api.models.entitysearch;

import com.hariansyah.bookyourrooms.api.models.entitymodels.responses.CityResponse;
import com.hariansyah.bookyourrooms.api.models.pagination.PageSearch;

import java.util.List;

public class RegionSearch extends PageSearch {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
