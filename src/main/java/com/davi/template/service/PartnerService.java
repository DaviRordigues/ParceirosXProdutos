package com.davi.template.service;

import com.davi.template.entity.PartnerEntity;
import com.davi.template.entity.ProductEntity;

import java.util.List;

public interface PartnerService {
    List<PartnerEntity> getAllPartners();
    PartnerEntity getPartnerById(String id);
    PartnerEntity createPartner(PartnerEntity partner);
    PartnerEntity updatePartner(String id, PartnerEntity partnerDetails);
    boolean deletePartner(String id);
    PartnerEntity addProductToPartner(String partnerId, ProductEntity product);
    void createBulkPartners();
}
