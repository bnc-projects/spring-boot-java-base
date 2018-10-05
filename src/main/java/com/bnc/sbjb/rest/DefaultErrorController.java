package com.bnc.sbjb.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultErrorController implements ErrorController {

    private static final String PATH = "/error";

    @RequestMapping(value = PATH)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void error() {
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
