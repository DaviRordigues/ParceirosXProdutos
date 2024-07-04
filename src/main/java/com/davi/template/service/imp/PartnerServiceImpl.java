package com.davi.template.service.imp;

import com.davi.template.entity.PartnerEntity;
import com.davi.template.entity.ProductEntity;
import com.davi.template.repositories.PartnerRepository;
import com.davi.template.service.PartnerService;
import com.davi.template.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PartnerServiceImpl implements PartnerService {
    @Value("${partners.file.path}")
    private String partnersFilePath;
    private final PartnerRepository partnerRepository;
    private final ProductService productService;

    public PartnerServiceImpl(PartnerRepository partnerRepository, ProductService productService) {
        this.partnerRepository = partnerRepository;
        this.productService = productService;
    }

    @Override
    public List<PartnerEntity> getAllPartners() {
        return partnerRepository.findAll();
    }

    @Override
    public PartnerEntity getPartnerById(String id) {
        return partnerRepository.findById(id).orElse(null);
    }

    @Override
    public PartnerEntity createPartner(PartnerEntity partner) {
        partner.setId(generateIdFromName(partner.getName()));
        if (partner.getProducts() != null) {
            for (ProductEntity product : partner.getProducts()) {
                productService.createProduct(product);
            }
        }
        return partnerRepository.save(partner);
    }

    @Override
    public PartnerEntity updatePartner(String id, PartnerEntity partnerDetails) {
        Optional<PartnerEntity> existingPartnerOpt = partnerRepository.findById(id);
        if (existingPartnerOpt.isEmpty()) {
            return null;
        }
        PartnerEntity existingPartner = existingPartnerOpt.get();
        if (partnerDetails.getName() != null) {
            existingPartner.setName(partnerDetails.getName());
        }
        if (partnerDetails.getProducts() != null) {
            existingPartner.setProducts(partnerDetails.getProducts());
        }
        return partnerRepository.save(existingPartner);
    }

    @Override
    public boolean deletePartner(String id) {
        if (!partnerRepository.existsById(id)) {
            return false;
        }
        partnerRepository.deleteById(id);
        return true;
    }

    @Override
    public void createBulkPartners() {
        // Implementation for bulk creation
    }

    @Override
    public PartnerEntity addProductToPartner(String partnerId, ProductEntity product) {
        Optional<PartnerEntity> optionalPartner = partnerRepository.findById(partnerId);
        if (optionalPartner.isPresent()) {
            PartnerEntity partner = optionalPartner.get();
            ProductEntity createdProduct = productService.createProduct(product);
            if (partner.getProducts() == null) {
                partner.setProducts(new ArrayList<>());
            }
            partner.getProducts().add(createdProduct);
            return partnerRepository.save(partner);
        }
        return null;
    }

    private String generateIdFromName(String name) {
        String[] words = name.split(" ");
        StringBuilder idBuilder = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                idBuilder.append(word.charAt(0));
            }
        }
        return idBuilder.toString().toLowerCase();
    }
}
