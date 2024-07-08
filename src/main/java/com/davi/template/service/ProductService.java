package com.davi.template.service;

import com.davi.template.dtos.ProductDTO;
import com.davi.template.dtos.requests.ProductRequestDTO;
import com.davi.template.entity.ProductEntity;

import java.util.List;

public interface ProductService {
	List<ProductDTO> getAllProducts(String partnerId);
	
	ProductEntity getProductBySkuId(String skuId);
	
	List<ProductDTO> createProduct(String partnerId, List<ProductRequestDTO> products);
	
	ProductDTO updateProduct(String skuId, ProductRequestDTO productRequestDTO);
	
	void deleteProduct(String skuId);
}
