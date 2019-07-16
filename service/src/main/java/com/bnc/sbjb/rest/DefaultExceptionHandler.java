package com.bnc.sbjb.rest;

import com.bnc.api.model.error.CustomError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class DefaultExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public CustomError handleException(Exception ex) {
        logger.error("Unknown exception errorMessage=\"{}\"]", ex.getMessage(), ex);
        return new CustomError(HttpStatus.INTERNAL_SERVER_ERROR,
            "Oops something went wrong. Please contact us if this keeps occurring.");
    }
}
