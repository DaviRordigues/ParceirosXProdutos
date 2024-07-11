package com.davi.template.ErrorCode;

import org.springframework.http.HttpStatus;
import java.util.HashMap;
import java.util.Map;

public class ErrorCode {
    private static final Map<HttpStatus, String> ERROR_CODES;

    static {
        ERROR_CODES = Map.ofEntries(
                Map.entry(HttpStatus.OK, "OK"),
                Map.entry(HttpStatus.CREATED, "Created"),
                Map.entry(HttpStatus.BAD_REQUEST, "Bad Request"),
                Map.entry(HttpStatus.NOT_FOUND, "Not Found"));
    }
    public static Map<String, Object> getErrorResponse(HttpStatus status, String code, String description) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("Error", getDescription(status));
        errorResponse.put("Code", code);
        errorResponse.put("Description", description);
        return errorResponse;
    }

    public static String getDescription(HttpStatus status) {
        return ERROR_CODES.getOrDefault(status, "Unknown Status");
    }

}
