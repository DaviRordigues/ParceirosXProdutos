package com.davi.template.exceptions.partner;

public class PartnerNotFoundException extends RuntimeException {
    public PartnerNotFoundException(String message) {
        super(message);
    }
}
