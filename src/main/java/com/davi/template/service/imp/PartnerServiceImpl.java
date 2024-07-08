package com.davi.template.service.imp;

import com.davi.template.dtos.PartnerDTO;
import com.davi.template.dtos.requests.PartnerRequestDTO;
import com.davi.template.entity.PartnerEntity;
import com.davi.template.repositories.PartnerRepository;
import com.davi.template.service.PartnerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartnerServiceImpl implements PartnerService {
	@Value("${partners.file.path}")
	private String partnersFilePath;
	private final PartnerRepository partnerRepository;
	
	public PartnerServiceImpl(PartnerRepository partnerRepository) {
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
	public PartnerDTO createPartner(PartnerRequestDTO partnerRequestDTO) {
		PartnerEntity partnerEntity = PartnerEntity
				.builder()
				.id(generateIdFromName(partnerRequestDTO.getName()))
				.name(partnerRequestDTO.getName())
				.build();
		
		partnerEntity = partnerRepository.save(partnerEntity);
		
		return PartnerDTO
				.builder()
				.id(partnerEntity.getId())
				.name(partnerEntity.getName())
				.products(partnerEntity.getProducts())
				.build();
	}
	
	@Override
	public PartnerDTO updatePartner(String id, PartnerRequestDTO partnerRequestDTO) {
		Optional<PartnerEntity> existingPartnerOpt = partnerRepository.findById(id);
		if (existingPartnerOpt.isPresent()) {
			PartnerEntity partnerEntity = PartnerEntity
					.builder()
					.id(generateIdFromName(partnerRequestDTO.getName()))
					.name(partnerRequestDTO.getName())
					.build();
			
			partnerEntity = partnerRepository.save(partnerEntity);
			
			return PartnerDTO
					.builder()
					.id(partnerEntity.getId())
					.name(partnerEntity.getName())
					.products(partnerEntity.getProducts())
					.build();
			
		}
		throw new RuntimeException("Cannot found partnerId: " + id);
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
