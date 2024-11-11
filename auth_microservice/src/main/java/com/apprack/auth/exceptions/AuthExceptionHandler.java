package com.apprack.auth.exceptions;

import com.apprack.auth.constants.HttpResponseCodes;
import com.apprack.auth.constants.HttpResponseMessages;
import com.apprack.auth.model.ApiResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class AuthExceptionHandler {

    @ExceptionHandler(UserAlreadyExistsException.class)
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> handleException(Exception ex) {
        ApiResponse<Void> response = ApiResponse.error(HttpResponseCodes.UNAUTHORIZED_CODE, HttpResponseMessages.USERNAME_ALREADY_EXISTS);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<Map<String, String>> handleExpiredJwtException(ExpiredJwtException ex) {
        // Create a response body with error details
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("timestamp", LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        errorResponse.put("status", String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        errorResponse.put("error", "Unauthorized");
        errorResponse.put("message", "JWT token is expired");
        errorResponse.put("details", ex.getMessage());

        // Return the error response with a 401 Unauthorized status
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
