package com.jo.bankapi.common;


import org.apache.coyote.Response;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    // Handling for malformed request body
    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = "Malformed JSON request body";
        return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, errorMsg, ex));
    }


    // Handles error when validation error occur e.g from Javax Validation with Hibernate Validator
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        String errorMsg = "Request Body Validation Failed";
        List<ApiSubError> errors = ex.getFieldErrors().stream().map(i -> new ApiValidationError(
                i.getObjectName(),
                i.getField(),
                i.getRejectedValue(),
                i.getDefaultMessage()
        )).collect(Collectors.toList());

        return buildResponseEntity(new ApiError(
                HttpStatus.BAD_REQUEST,
                errorMsg,
                errors
        ));
    }

    private ResponseEntity<Object> buildResponseEntity(ApiError apiError) {
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
