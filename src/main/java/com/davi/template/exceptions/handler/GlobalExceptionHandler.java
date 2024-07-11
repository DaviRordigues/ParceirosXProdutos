package com.davi.template.exceptions;

import com.davi.template.ErrorCode.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SkuNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSkuNotFoundException(SkuNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String code = "SKU_NOT_FOUND";
        String description = ex.getMessage();

        Map<String, Object> errorResponse = ErrorCode.getErrorResponse(status, code, description);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(ProductNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String code = "PRODUCT_NOT_FOUND";
        String description = ex.getMessage();

        Map<String, Object> errorResponse = ErrorCode.getErrorResponse(status, code, description);

        return new ResponseEntity<>(errorResponse, status);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        String code = "INTERNAL_SERVER_ERROR";
        String description = ex.getMessage();

        Map<String, Object> errorResponse = ErrorCode.getErrorResponse(status, code, description);

        return new ResponseEntity<>(errorResponse, status);
    }
}
