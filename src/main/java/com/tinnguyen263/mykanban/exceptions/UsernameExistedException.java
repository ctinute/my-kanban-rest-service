package com.tinnguyen263.mykanban.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "This username has been used by another account")
public class UsernameExistedException extends Exception {

}
