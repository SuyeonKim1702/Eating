package com.eating.jinwoo.common;

import com.eating.jinwoo.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
@RestController
public class GlobalExceptionHandler {

    @ExceptionHandler(value = EatingException.class)
    public ResponseDTO<String> handleGlobalException(EatingException e) {
        return new ResponseDTO<String>(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
    }
}
