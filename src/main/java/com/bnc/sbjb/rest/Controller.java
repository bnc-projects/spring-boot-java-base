package com.bnc.sbjb.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${resource.path}")
public class Controller {

    @RequestMapping("/hello")
    @ResponseStatus(HttpStatus.OK)
    public String helloWorld() {
        return "Hello World";
    }
}
