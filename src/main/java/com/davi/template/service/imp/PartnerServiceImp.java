package com.davi.template.service.imp;

import com.davi.template.entity.PartnerEntity;
import com.davi.template.entity.ProductEntity;
import com.davi.template.repositories.PartnerRepository;
import com.davi.template.service.PartnerService;
import org.springframework.stereotype.Service;

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
        existingPartner.setName(partnerDetails.getName());
        if (partnerDetails.getProducts() != null) {
            existingPartner.setProducts(partnerDetails.getProducts());
            for (ProductEntity product : existingPartner.getProducts()) {
                product.setSkuId(generateSkuId());
            }
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
}
