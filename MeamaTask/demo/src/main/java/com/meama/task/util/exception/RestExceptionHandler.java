package com.meama.task.util.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Object> handleError(AccessDeniedException ex){
        MyErrorResponse errorResponse = new MyErrorResponse(HttpStatus.FORBIDDEN,"access.denied", "");
        log.error(errorResponse.toString());
        return buildResponseEntity(errorResponse);
    }


    @ExceptionHandler(MyException.class)
    protected ResponseEntity<Object> handleError(MyException ex) {
        MyErrorResponse errorResponse = new MyErrorResponse(ex);
        log.error(errorResponse.toString());
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<Object> buildResponseEntity(MyErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, errorResponse.getStatus());
    }
}
