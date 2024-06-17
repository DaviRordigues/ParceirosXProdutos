package com.davi.template.service.imp;

import com.davi.template.entity.PartnerEntity;
import com.davi.template.entity.ProductEntity;
import com.davi.template.repositories.PartnerRepository;
import com.davi.template.service.PartnerService;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return partnerRepository.save(partner);
    }

    @Override
    public PartnerEntity updatePartner(String id, PartnerEntity partnerDetails) {
        PartnerEntity existingPartner = partnerRepository.findById(id).orElse(null);
        if (existingPartner == null) {
            return null;
        }
        existingPartner.setName(partnerDetails.getName());
        existingPartner.setProducts(partnerDetails.getProducts());  // Adicionar esta linha
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
        PartnerEntity partner = getPartnerById(partnerId);
        if (partner == null) {
            return null;
        }
        partner.getProducts().add(product);
        return partnerRepository.save(partner);
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