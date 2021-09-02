package com.kry.demo.pollingService.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidUrlException extends RuntimeException {

    public InvalidUrlException(String message) { super(message); }
}
