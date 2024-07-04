package com.davi.template.service.imp;

import com.davi.template.entity.ProductEntity;
import com.davi.template.repositories.ProductRepository;
import com.davi.template.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductEntity> getAllProducts() {
        logger.info("Fetching all products");
        return productRepository.findAll();
    }

    @Override
    public ProductEntity getProductBySkuId(String skuId) {
        logger.info("Fetching product by SKU ID: {}", skuId);
        return productRepository.findById(skuId).orElse(null);
    }

    @Override
    public List<ProductEntity> getProductsByPartnerId(String partnerId) {
        logger.info("Fetching products by partner ID: {}", partnerId);
        return productRepository.findByPartnerId(partnerId);
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        String skuId = generateSkuId();
        while (productRepository.existsById(skuId)) {
            skuId = generateSkuId();
        }
        product.setSkuId(skuId);
        product.setCategory(generateCategoryId());
        logger.info("Creating product with SKU ID: {}", skuId);
        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(String skuId, ProductEntity productDetails) {
        logger.info("Updating product with SKU ID: {}", skuId);
        ProductEntity existingProduct = productRepository.findById(skuId).orElse(null);
        if (existingProduct == null) {
            logger.error("Product not found with SKU ID: {}", skuId);
            return null;
        }
        if (productDetails.getName() != null) {
            existingProduct.setName(productDetails.getName());
        }
        if (productDetails.getPrice() != 0) {
            existingProduct.setPrice(productDetails.getPrice());
        }
        if (productDetails.getCategory() != null) {
            existingProduct.setCategory(productDetails.getCategory());
        }
        return productRepository.save(existingProduct);
    }

    @Override
    public boolean deleteProduct(String skuId) {
        logger.info("Deleting product with SKU ID: {}", skuId);
        if (!productRepository.existsById(skuId)) {
            logger.error("Product not found with SKU ID: {}", skuId);
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

    private String generateCategoryId() {
        Random random = new Random();
        StringBuilder categoryId = new StringBuilder("CAT");
        for (int i = 0; i < 5; i++) {
            int nextChar = random.nextInt(36);
            if (nextChar < 10) {
                categoryId.append((char) ('0' + nextChar));
            } else {
                categoryId.append((char) ('A' + nextChar - 10));
            }
        }
        return categoryId.toString();
    }
}
