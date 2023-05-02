package com.mark.code.epamlab.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Iternal_Server_Error!!!!!!")
public class InternalServerError extends Exception{
    public InternalServerError(String message) {
        super(message);
    }

}
