package com.davi.template.controllers;

import com.davi.template.dtos.ProductDTO;
import com.davi.template.dtos.requests.ProductRequestDTO;
import com.davi.template.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products/")
@AllArgsConstructor
public class ProductController {

	private final ProductService productService;

	//TODO: a paginação nao possui apenas page a size, melhor do que enviar apenas um page e um size, experimente enviar o Pageable

	@GetMapping("partner/{partnerId}")
	public ResponseEntity<List<ProductDTO>> getAllProductsFromPartner(
			@PathVariable String partnerId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		List<ProductDTO> products = productService.getAllProducts(partnerId, page, size);
		return ResponseEntity.ok(products);
	}


	@GetMapping("sku/{skuId}")
	public ResponseEntity<ProductDTO> getProductBySkuId(@PathVariable String skuId) {
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

	@PostMapping("/partner/{partnerId}/bulk-add")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void addBulkProductsToPartner(@PathVariable String partnerId) {
		productService.addBulkProductsToPartner(partnerId);
	}

	@DeleteMapping("/{skuId}")
	public ResponseEntity<Void> deleteProduct(@PathVariable String skuId) {
		productService.deleteProduct(skuId);
		return ResponseEntity.noContent().build();
	}
}
