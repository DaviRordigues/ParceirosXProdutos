package com.davi.template.service.imp;

import com.davi.template.entity.PartnerEntity;
import com.davi.template.entity.ProductEntity;
import com.davi.template.repositories.PartnerRepository;
import com.davi.template.service.PartnerService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class PartnerServiceImp implements PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerServiceImp(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
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
                product.setSkuId(generateSkuId());
                product.setCategory(generateCategoryId()); // Gerar categoria
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
    public PartnerEntity addProductToPartner(String partnerId, ProductEntity product) {
        return null;
    }

    public List<PartnerEntity> createBulkPartners(int numPartners, int numProductsPerPartner) {
        List<PartnerEntity> partners = new ArrayList<>();
        for (int i = 0; i < numPartners; i++) {
            List<ProductEntity> products = new ArrayList<>();
            for (int j = 0; j < numProductsPerPartner; j++) {
                products.add(ProductEntity.builder()
                        .skuId(generateSkuId())
                        .name("Product " + j)
                        .price(new Random().nextDouble() * 100)
                        .category(generateCategoryId())
                        .build());
            }
            partners.add(PartnerEntity.builder()
                    .id(generateIdFromName("Partner " + i))
                    .name("Partner " + i)
                    .products(products)
                    .build());
        }
        return partnerRepository.saveAll(partners);
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

    private String generateSkuId() {
        Random random = new Random();
        int number = 100000 + random.nextInt(900000);
        return "SKU" + number;
    }

    private String generateCategoryId() {
        Random random = new Random();
        StringBuilder categoryId = new StringBuilder("LIV");
        for (int i = 0; i < 5; i++) {
            int nextChar = random.nextInt(36); // 0-9 and A-Z
            if (nextChar < 10) {
                categoryId.append((char) ('0' + nextChar));
            } else {
                categoryId.append((char) ('A' + nextChar - 10));
            }
        }
        return categoryId.toString();
    }
}
