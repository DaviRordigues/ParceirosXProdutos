package com.davi.template.service;

import com.davi.template.dtos.PartnerDTO;
import com.davi.template.dtos.requests.PartnerRequestDTO;
import com.davi.template.entity.PartnerEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PartnerService {
	List<PartnerDTO> getAllPartners(Pageable pageable);

	PartnerDTO getPartnerById(String id);

	PartnerDTO createPartner(PartnerRequestDTO partnerRequestDTO);

	PartnerDTO updatePartner(String id, PartnerRequestDTO partnerDetails);

	PartnerEntity findPartnerById(String id);

	void deletePartner(String id);

	void createBulkPartners();
}
