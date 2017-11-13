package com.tinnguyen263.mykanban.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Only admin can access")
public class NotAdminException extends Exception {
}
