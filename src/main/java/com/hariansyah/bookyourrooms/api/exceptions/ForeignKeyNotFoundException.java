package com.hariansyah.bookyourrooms.api.exceptions;

import org.springframework.http.HttpStatus;

public class ForeignKeyNotFoundException extends ApplicationException {

    public ForeignKeyNotFoundException() {
        super(HttpStatus.NOT_FOUND, "error." + HttpStatus.NOT_FOUND.value() + ".foreign_key");
    }
}
