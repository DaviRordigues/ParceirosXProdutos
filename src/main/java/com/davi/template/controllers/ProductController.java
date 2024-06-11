package com.davi.template.controllers;

import com.davi.template.entity.ProductEntity;

import com.davi.template.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductEntity>> getAllProducts() {
        List<ProductEntity> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{skuId}")
    public ResponseEntity<ProductEntity> getProductBySkuId(@PathVariable String skuId) {
        ProductEntity product = productService.getProductBySkuId(skuId);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    @PostMapping
    public ResponseEntity<ProductEntity> createProduct(@RequestBody ProductEntity product) {
        ProductEntity createdProduct = productService.createProduct(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping("/{skuId}")
    public ResponseEntity<ProductEntity> updateProduct(@PathVariable String skuId, @RequestBody ProductEntity productDetails) {
        ProductEntity updatedProduct = productService.updateProduct(skuId, productDetails);
        if (updatedProduct == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{skuId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable String skuId) {
        boolean deleted = productService.deleteProduct(skuId);
        if (!deleted) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
