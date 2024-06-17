package com.davi.template.service;

import com.davi.template.entity.ProductEntity;

import java.util.List;

public interface ProductService {
    List<ProductEntity> getAllProducts();
    ProductEntity getProductBySkuId(String skuId);
    List<ProductEntity> getProductsByPartnerId(String partnerId);
    ProductEntity createProduct(ProductEntity product);
    ProductEntity updateProduct(String skuId, ProductEntity productDetails);
    boolean deleteProduct(String skuId);
}
