package com.bnc.sbjb.rest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import com.bnc.sbjb.domain.v1.ErrorResponse;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler({
        IllegalStateException.class,
        InterruptedException.class,
        ExecutionException.class,
        Throwable.class})
    @ResponseStatus(value = INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ResponseEntity<ErrorResponse> internalServerError(HttpServletRequest request, Exception exception) {
        logger.error("Server error [path={}, errorMessage=\"{}\"]",request.getRequestURI(), exception.getMessage(), exception);

        return new ResponseEntity<>(new ErrorResponse(INTERNAL_SERVER_ERROR, null), INTERNAL_SERVER_ERROR);
    }
}
