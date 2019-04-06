package com.bnc.sbjb.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${resource.path}")
public class Controller {

    @RequestMapping("/hello-error")
    @ResponseStatus(HttpStatus.OK)
    public String notYetWorld() {
        throw new IllegalStateException("Hello World");
    }

    @RequestMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public String helloWorld() {
        return "Hello World";
    }
}
