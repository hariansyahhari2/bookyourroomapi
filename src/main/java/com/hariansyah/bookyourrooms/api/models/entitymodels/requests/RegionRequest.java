package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class RegionRequest {

    private Integer id;

    @NotBlank
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
