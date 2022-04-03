package com.meama.task.util.exception;

import org.springframework.http.HttpStatus;

public class ExceptionFactory {

    public static MyException userNotFound(){
        return new MyException("not.found","No user with this username", HttpStatus.NOT_FOUND);
    }

    public static MyException roleNotFound(){
        return new MyException("not.found","No role with this name", HttpStatus.NOT_FOUND);
    }

    public static MyException taskNotFound(){
        return new MyException("not.found","No task with this id", HttpStatus.NOT_FOUND);
    }

    public static MyException constraintViolated(String message){
        return new MyException("constraint.violated",message, HttpStatus.CONFLICT);
    }



}
