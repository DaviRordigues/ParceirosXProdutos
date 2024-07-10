package com.davi.template.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(SkuNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleSkuNotFoundException(SkuNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("Error", "Sku Not Found");
        errorResponse.put("Code", "SKU_NOT_FOUND");
        errorResponse.put("Description", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFoundException(ProductNotFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("Error", "Product Not Found");
        errorResponse.put("Code", "PRODUCT_NOT_FOUND");
        errorResponse.put("Description", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("Error", "Internal Server Error");
        errorResponse.put("Code", "INTERNAL_SERVER_ERROR");
        errorResponse.put("Description", ex.getMessage());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
