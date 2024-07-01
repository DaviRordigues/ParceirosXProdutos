package com.davi.template.service.imp;

import com.davi.template.entity.ProductEntity;
import com.davi.template.repositories.ProductRepository;
import com.davi.template.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImp(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getProductBySkuId(String skuId) {
        return productRepository.findById(skuId).orElse(null);
    }

    @Override
    public List<ProductEntity> getProductsByPartnerId(String partnerId) {
        return null;
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        product.setSkuId(generateSkuId());
        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(String skuId, ProductEntity productDetails) {
        ProductEntity existingProduct = productRepository.findById(skuId).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());
        return productRepository.save(existingProduct);
    }

    @Override
    public boolean deleteProduct(String skuId) {
        if (!productRepository.existsById(skuId)) {
            return false;
        }
        productRepository.deleteById(skuId);
        return true;
    }

    private String generateSkuId() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return "SKU" + number;
    }
}
