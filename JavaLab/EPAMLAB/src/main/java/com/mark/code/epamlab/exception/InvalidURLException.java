package com.mark.code.epamlab.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "invalid arguments!!!!!!!!!!!!!!!!")
public class InvalidURLException extends Exception {
    public InvalidURLException(String message) {
        super(message);
    }


}
