package com.davi.template.exceptions.handler;

import com.davi.template.constants.ErrorCodes;
import com.davi.template.exceptions.ExceptionResponse;
import com.davi.template.exceptions.partner.PartnerNotFoundException;
import com.davi.template.exceptions.sku.SkuNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		log.error("GlobalExceptionHandler.handleAllExceptions - Unmapped error has occurred while processing HTTP request: [{}]", request, ex);

		ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.INTERNAL_SERVER_ERROR, ex.getMessage());

		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exceptionResponse);
	}

	@ExceptionHandler(SkuNotFoundException.class)
	public ResponseEntity<Object> handleSkuNotFoundException(SkuNotFoundException ex, WebRequest request) {
		log.error("GlobalExceptionHandler.handleSkuNotFoundException - Cannot found sku in HTTP request: [{}]", request, ex);

		ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.SKU_NOT_FOUND, ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}

	@ExceptionHandler(PartnerNotFoundException.class)
	public ResponseEntity<Object> handlePartnerNotFoundException(PartnerNotFoundException ex, WebRequest request) {
		log.error("GlobalExceptionHandler.handlePartnerNotFoundException - Cannot found partner in HTTP request: [{}]", request, ex);

		ExceptionResponse exceptionResponse = new ExceptionResponse(ErrorCodes.PARTNER_NOT_FOUND, ex.getMessage());

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exceptionResponse);
	}
}
