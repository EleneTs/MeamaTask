package com.meama.task.util.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MyException extends RuntimeException {

    private String errorKey;
    private String errorDescription;
    private HttpStatus status;

    public MyException(String errorKey, String errorDescription, HttpStatus status){
        super(errorKey);
        this.errorKey = errorKey;
        this.errorDescription = errorDescription;
        this.status = status;
    }

}
