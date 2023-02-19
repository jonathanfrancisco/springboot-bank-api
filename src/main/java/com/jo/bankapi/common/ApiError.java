package com.jo.bankapi.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private HttpStatus status;
    private LocalDateTime timestamp;
    private String message;
    private String debugMessage;
    private List<ApiSubError> subErrors;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus status, String message, Throwable ex) {
        this();
        this.status = status;
        this.message = message;
//        this.debugMessage = ex.getLocalizedMessage();
    }

    ApiError(HttpStatus status, String message, List<ApiSubError> subErrors) {
        this();
        this.status = status;
        this.message = message;
        this.subErrors = subErrors;
    }
}
