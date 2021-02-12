package com.hariansyah.bookyourrooms.api.exceptions;

import org.springframework.http.HttpStatus;

public class RoomNotAvailableException extends ApplicationException {

    public RoomNotAvailableException() {
        super(HttpStatus.BAD_REQUEST, "error." + HttpStatus.BAD_REQUEST.value() + ".not-available");
    }
}