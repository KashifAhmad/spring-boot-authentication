package prod_mng_microservice.exception;

import auth_mircroservice.constants.HttpResponseCodes;
import auth_mircroservice.constants.HttpResponseMessages;
import auth_mircroservice.model.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleException(Exception e) {
        System.err.println("Exception caught: " + e.getMessage()); // Log the exception message
        return new ResponseEntity<>(ApiResponse.error(500, e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
