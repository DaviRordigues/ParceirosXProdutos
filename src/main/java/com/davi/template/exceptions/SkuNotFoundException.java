package com.davi.template.exceptions;

public class SkuNotFoundException extends RuntimeException {
    public SkuNotFoundException(String message) {
        super(message);
    }
}
