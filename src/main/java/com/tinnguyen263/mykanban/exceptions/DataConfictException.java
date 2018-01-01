package com.tinnguyen263.mykanban.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class DataConfictException extends RuntimeException {
    public DataConfictException(String message) {
        super(message);
    }
}
