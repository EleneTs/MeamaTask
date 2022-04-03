package com.meama.task.util.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MyErrorResponse {
    private HttpStatus status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timeStamp;

    private String messageCode;

    private String messageDescription;

    private MyErrorResponse(){
        this.timeStamp = LocalDateTime.now();
    }

    public MyErrorResponse(HttpStatus status, String messageCode, String messageDescription){
        this();
        this.status = status;
        this.messageCode = messageCode;
        this.messageDescription = messageDescription;
    }

   public MyErrorResponse(MyException exception){
        this(exception.getStatus(),exception.getErrorKey(),exception.getErrorDescription());
   }

//    public MyErrorResponse(Exception exception){
//        this(HttpStatus.INTERNAL_SERVER_ERROR,"general.server.error",exception.getLocalizedMessage());
//    }





}
