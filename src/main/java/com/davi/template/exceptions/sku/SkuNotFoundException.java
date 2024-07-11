package com.davi.template.exceptions.sku;

public class SkuNotFoundException extends RuntimeException {
	public SkuNotFoundException(String skuId) {
		super("Cannot found skuId with id: " + skuId);
	}
}
