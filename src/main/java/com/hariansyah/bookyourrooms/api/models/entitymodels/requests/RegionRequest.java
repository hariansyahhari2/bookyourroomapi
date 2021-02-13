package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class RegionRequest {

    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
