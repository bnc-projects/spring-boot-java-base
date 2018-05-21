package com.bnc.sbjb.rest;

import static javax.servlet.RequestDispatcher.ERROR_STATUS_CODE;
import static javax.servlet.RequestDispatcher.FORWARD_REQUEST_URI;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/error")
public class SimpleErrorController implements ErrorController {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @Override
    public String getErrorPath() {
        return "/error";
    }

    @RequestMapping
    public String error(HttpServletRequest request) {
        logger.warn("[status={}, path={}]", request.getAttribute(ERROR_STATUS_CODE), request.getAttribute(FORWARD_REQUEST_URI));

        return "404";
    }
}
