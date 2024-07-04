package com.davi.template.controllers;

import com.davi.template.entity.ProductEntity;
import com.davi.template.entity.PartnerEntity;
import com.davi.template.service.ProductService;
import com.davi.template.service.PartnerService;
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
    private final PartnerService partnerService;

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

    @PostMapping("/{partnerId}/addProduct")
    public ResponseEntity<PartnerEntity> addProductToPartner(@PathVariable String partnerId, @RequestBody ProductEntity product) {
        PartnerEntity updatedPartner = partnerService.addProductToPartner(partnerId, product);
        if (updatedPartner == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedPartner);
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
