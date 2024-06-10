package com.davi.template.Service;
import com.davi.template.Entity.PartnerEntity;


import java.util.List;

public interface PartnerService {
    List<PartnerEntity> getAllPartners();
    PartnerEntity getPartnerById(String id);
    PartnerEntity createPartner(PartnerEntity partner);
    PartnerEntity updatePartner(String id, PartnerEntity partnerDetails);
    boolean deletePartner(String id);
}
