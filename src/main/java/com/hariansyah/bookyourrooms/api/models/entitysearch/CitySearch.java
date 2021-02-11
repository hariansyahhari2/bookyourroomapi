package com.hariansyah.bookyourrooms.api.models.entitysearch;

import com.hariansyah.bookyourrooms.api.models.pagination.PageSearch;

public class CitySearch extends PageSearch {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
