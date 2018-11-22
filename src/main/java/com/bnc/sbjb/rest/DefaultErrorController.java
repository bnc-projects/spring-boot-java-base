package com.bnc.sbjb.rest;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultErrorController implements ErrorController {

    private static final String PATH = "/error";

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @RequestMapping(value = PATH)
    public void error() {
        // Blank method since we don't need to do anything to handle unknown paths.
        // Any forked service may choose to handle some requests differently.
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
