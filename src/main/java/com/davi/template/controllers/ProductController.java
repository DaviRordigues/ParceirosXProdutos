package com.davi.template.controllers;

import com.davi.template.dtos.ProductDTO;
import com.davi.template.dtos.requests.ProductRequestDTO;
import com.davi.template.entity.ProductEntity;
import com.davi.template.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("products/")
@AllArgsConstructor
public class ProductController {
	
	private final ProductService productService;
	
	@GetMapping("partner/{partnerId}")
	public ResponseEntity<List<ProductDTO>> getAllProductsFromPartner(@PathVariable String partnerId) {
		return ResponseEntity.ok(productService.getAllProducts(partnerId));
	}
	
	//TODO: MODIFICAR PARA DTO
	@GetMapping("sku/{skuId}")
	public ResponseEntity<ProductEntity> getProductBySkuId(@PathVariable String skuId) {
		return ResponseEntity.ok(productService.getProductBySkuId(skuId));
	}
	
	@PostMapping("partner/{partnerId}")
	public ResponseEntity<List<ProductDTO>> createProduct(@PathVariable String partnerId,
	                                                      @RequestBody List<ProductRequestDTO> productRequestDTOs) {
		List<ProductDTO> createdProducts = productService.createProduct(partnerId, productRequestDTOs);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdProducts);
	}
	
	@PutMapping("/{skuId}")
	public ResponseEntity<ProductDTO> updateProduct(@PathVariable String skuId,
	                                                @RequestBody ProductRequestDTO productRequestDTO) {
		return ResponseEntity.ok(productService.updateProduct(skuId, productRequestDTO));
	}
	
	@DeleteMapping("/{skuId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String skuId) {
		productService.deleteProduct(skuId);
		return ResponseEntity.noContent().build();
	}
}
