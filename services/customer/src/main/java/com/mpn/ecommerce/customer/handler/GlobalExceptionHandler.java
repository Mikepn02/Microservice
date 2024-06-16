package com.mpn.ecommerce.customer.handler;


import com.mpn.ecommerce.customer.exception.CustomerNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<String> handle(CustomerNotFoundException exp) {
        return ResponseEntity.status(NOT_FOUND)
                .body(exp.getMsg());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handle(MethodArgumentNotValidException exp) {
        var errors = new HashMap<String , String>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var fieldName =((FieldError) error).getField();
                    var errMessage = error.getDefaultMessage();
                    errors.put(fieldName , errMessage);
                });
        return ResponseEntity.status(BAD_REQUEST)
                .body(new ErrorResponse(errors));
    }
}
