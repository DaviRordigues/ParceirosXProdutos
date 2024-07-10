package com.davi.template.exceptions;

public class SkuNotFoundException extends RuntimeException {
	// TODO: NECESSÁRIO TESTAR ESSA EXCEPTION NO POSTMAN, SEM UM HANDLER A EXEÇÃO MESMO QUE PERSONALIZADA NAO É MOSTRADA
	
	public SkuNotFoundException(String skuId) {
		super("Cannot found skuId with id: " + skuId);
	}
}
