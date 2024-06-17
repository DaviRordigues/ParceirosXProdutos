package com.davi.template.service.imp;

import com.davi.template.entity.ProductEntity;
import com.davi.template.repositories.ProductRepository;
import com.davi.template.repositories.PartnerRepository;
import com.davi.template.service.ProductService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Random;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepository productRepository;
    private final PartnerRepository partnerRepository;

    public ProductServiceImp(ProductRepository productRepository, PartnerRepository partnerRepository) {
        this.productRepository = productRepository;
        this.partnerRepository = partnerRepository;
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
        return productRepository.findByPartnerId(partnerId);
    }

    @Override
    public ProductEntity createProduct(ProductEntity product) {
        if (product.getPartnerId() == null || !partnerRepository.existsById(product.getPartnerId())) {
            throw new IllegalArgumentException("Invalid partnerId");
        }
        product.setSkuId(generateSkuId());
        return productRepository.save(product);
    }

    @Override
    public ProductEntity updateProduct(String skuId, ProductEntity productDetails) {
        ProductEntity existingProduct = productRepository.findById(skuId).orElse(null);
        if (existingProduct == null) {
            return null;
        }
        if (productDetails.getPartnerId() == null || !partnerRepository.existsById(productDetails.getPartnerId())) {
            throw new IllegalArgumentException("Invalid partnerId");
        }
        existingProduct.setName(productDetails.getName());
        existingProduct.setPrice(productDetails.getPrice());
        existingProduct.setPartnerId(productDetails.getPartnerId());
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
