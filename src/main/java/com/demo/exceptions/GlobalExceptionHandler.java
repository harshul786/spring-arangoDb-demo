package com.demo.exceptions;

import com.demo.models.ResponseModels.CustomResponseModel;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({IllegalArgumentException.class, RuntimeException.class, HttpMediaTypeNotAcceptableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomResponseModel<?>> handleIllegalArgumentException(Exception ex, HttpServletRequest request) {
        String errorMessage = ex.getMessage();
        Object method = request.getMethod();

        CustomResponseModel<?> errorResponse = CustomResponseModel.<Object>builder()
                .isSuccess(false)
                .errorMessage(errorMessage + " " + method)
                .data(null)
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler({ExpiredJwtException.class, AccessDeniedException.class})
    public ResponseEntity<CustomResponseModel<?>> handleAccessDeniedException(AccessDeniedException ex, HttpServletRequest request) {
        String errorMessage = ex.getMessage();
        Object method = request.getMethod();

        CustomResponseModel<?> errorResponse = CustomResponseModel.<Object>builder()
                .isSuccess(false)
                .errorMessage(errorMessage + " " + method)
                .data(null)
                .statusCode(HttpStatus.FORBIDDEN.value())
                .build();

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }



    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomResponseModel<?>> handleAllExceptions(Exception ex) {
        String errorMessage = ex.getMessage();

        CustomResponseModel<?> errorResponse = CustomResponseModel.<Object>builder()
                .isSuccess(false)
                .errorMessage(errorMessage)
                .data(null)
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

}