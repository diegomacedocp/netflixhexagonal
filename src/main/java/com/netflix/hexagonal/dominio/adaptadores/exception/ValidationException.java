package com.netflix.hexagonal.dominio.adaptadores.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ValidationException extends Exception{
    private static final long serialVersionUID = 1476542066938633226L;

    public ValidationException(String message){
        super(message);
    }
}
