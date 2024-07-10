package com.davi.template.exceptions;

public class ProductNotFoundException extends RuntimeException {
	// TODO: NECESSÁRIO TESTAR ESSA EXCEPTION NO POSTMAN, SEM UM HANDLER A EXEÇÃO MESMO QUE PERSONALIZADA NAO É MOSTRADA
	public ProductNotFoundException(String skuId) {
		super(String.format("Cannot found skuId with id: %s in seller", skuId));
	}
}
