package com.tinnguyen263.mykanban.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This email has been used by another account")
public class EmailExistedException extends Exception {
}
