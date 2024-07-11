package com.davi.template.ErrorCode;

import org.springframework.http.HttpStatus;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
    private static final Map<HttpStatus, String> ERROR_CODES;

    static {
        Map<HttpStatus, String> codes = new HashMap<>();
        codes.put(HttpStatus.CONTINUE, "Continue");
        codes.put(HttpStatus.SWITCHING_PROTOCOLS, "Switching Protocols");
        codes.put(HttpStatus.OK, "OK");
        codes.put(HttpStatus.CREATED, "Created");
        codes.put(HttpStatus.ACCEPTED, "Accepted");
        codes.put(HttpStatus.NO_CONTENT, "No Content");
        codes.put(HttpStatus.MOVED_PERMANENTLY, "Moved Permanently");
        codes.put(HttpStatus.FOUND, "Found");
        codes.put(HttpStatus.NOT_MODIFIED, "Not Modified");
        codes.put(HttpStatus.BAD_REQUEST, "Bad Request");
        codes.put(HttpStatus.UNAUTHORIZED, "Unauthorized");
        codes.put(HttpStatus.FORBIDDEN, "Forbidden");
        codes.put(HttpStatus.NOT_FOUND, "Not Found");
        codes.put(HttpStatus.METHOD_NOT_ALLOWED, "Method Not Allowed");
        codes.put(HttpStatus.CONFLICT, "Conflict");
        codes.put(HttpStatus.UNPROCESSABLE_ENTITY, "Unprocessable Entity");
        codes.put(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        codes.put(HttpStatus.NOT_IMPLEMENTED, "Not Implemented");
        codes.put(HttpStatus.BAD_GATEWAY, "Bad Gateway");
        codes.put(HttpStatus.SERVICE_UNAVAILABLE, "Service Unavailable");
        codes.put(HttpStatus.GATEWAY_TIMEOUT, "Gateway Timeout");
        ERROR_CODES = Collections.unmodifiableMap(codes);
    }
    public static String getDescription(HttpStatus status) {
        return ERROR_CODES.getOrDefault(status, "Unknown Status");
    }

    public static Map<String, Object> getErrorResponse(HttpStatus status, String code, String description) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("Error", getDescription(status));
        errorResponse.put("Code", code);
        errorResponse.put("Description", description);
        return errorResponse;
    }
    public static void main(String[] args) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String code = "PRODUCT_NOT_FOUND";
        String description = "The requested product was not found in the system.";
        Map<String, Object> errorResponse = getErrorResponse(status, code, description);
        System.out.println(errorResponse);
    }
}
