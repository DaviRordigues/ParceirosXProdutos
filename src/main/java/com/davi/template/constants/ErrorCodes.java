package com.davi.template.constants;

import lombok.Getter;

@Getter
public enum ErrorCodes {
	INTERNAL_SERVER_ERROR("Internal server error"),
	INVALID_REQUEST("Invalid request"),
	VALIDATION_FAILED("Validation failed"),
	PRODUCT_NOT_FOUND("Product not found"),
	MEDIA_NOT_FOUND("Media not found"),
	INVENTORY_NOT_FOUND("Inventory not found"),
	PRICE_NOT_FOUND("Price not found"),
	SKU_NOT_FOUND("Sku not found"),
	PARTNER_NOT_FOUND("PARTNER_NOT_FOUND"),
	PROCESS_NOT_FOUND("Process not found"),
	REPROCESS_NOT_FOUND("Reprocess not found"),
	PRODUCT_NOT_PUBLISHED("Product not published"),
	SKU_NOT_PUBLISHED("Sku not published");
	
	private final String message;
	
	ErrorCodes(String message) {
		this.message = message;
	}
	
}
