package com.kry.demo.pollingService.errors;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class WebServiceNotFoundException extends NotFoundException {

    public WebServiceNotFoundException(String message) {super(message);}
}
