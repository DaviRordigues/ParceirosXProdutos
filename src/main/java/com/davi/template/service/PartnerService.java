package com.davi.template.service;

import com.davi.template.dtos.PartnerDTO;
import com.davi.template.dtos.requests.PartnerRequestDTO;
import com.davi.template.entity.PartnerEntity;
import com.davi.template.entity.ProductEntity;
import java.util.List;

public interface PartnerService {
    List<PartnerEntity> getAllPartners();
    PartnerEntity getPartnerById(String id);
    PartnerDTO createPartner(PartnerRequestDTO partnerRequestDTO);
    PartnerEntity updatePartner(String id, PartnerEntity partnerDetails);
    boolean deletePartner(String id);
    void createBulkPartners();
    PartnerEntity addProductToPartner(String partnerId, ProductEntity product);
}
