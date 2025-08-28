package com.kabirit.accounts_service.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author nasimkabir
 * @date 7/4/25
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(CustomResponseStatusException.class)
    public ResponseEntity<Response> handleRoleNotFound(CustomResponseStatusException ex) {
        Response response = ResponseBuilder.getFailureResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseBuilder.getFailureResponse(ex.getBindingResult(), "Validation failed");
    }
}
