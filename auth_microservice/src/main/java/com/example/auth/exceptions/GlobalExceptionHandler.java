package com.example.auth.exceptions;

import com.example.auth.constants.HttpResponseCodes;
import com.example.auth.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ex.printStackTrace();
        ApiResponse<Void> response = ApiResponse.error(HttpResponseCodes.UNAUTHORIZED_CODE, auth_microservice.HttpResponseMessages.USERNAME_ALREADY_EXISTS);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
