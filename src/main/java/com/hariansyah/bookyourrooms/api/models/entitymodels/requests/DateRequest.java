package com.hariansyah.bookyourrooms.api.models.entitymodels.requests;

import java.time.LocalDate;

public class DateRequest {
    private LocalDate firstDate;

    private LocalDate lastDate;

    public LocalDate getFirstDate() {
        return firstDate;
    }

    public void setFirstDate(LocalDate firstDate) {
        this.firstDate = firstDate;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }
}
