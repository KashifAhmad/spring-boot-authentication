package com.apprack.pm_ms.exception;

import com.apprack.pm_ms.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        System.err.println("Exception caught: " + e.getMessage()); // Log the exception message
        return new ResponseEntity<>(ApiResponse.error(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
