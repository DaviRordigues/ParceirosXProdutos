package com.davi.template.service.imp;

import com.davi.template.dtos.PartnerDTO;
import com.davi.template.dtos.requests.PartnerRequestDTO;
import com.davi.template.entity.PartnerEntity;
import com.davi.template.exceptions.file.ReadFileException;
import com.davi.template.exceptions.partner.PartnerNotFoundException;
import com.davi.template.repositories.PartnerRepository;
import com.davi.template.service.PartnerService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
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
	public List<PartnerDTO> getAllPartners() {
		List<PartnerEntity> partners = partnerRepository.findAll();
		return partners.stream()
				.map(this::createPartnerDTOFromPartnerEntity)
				.toList();
	}
	
	@Override
	public PartnerDTO getPartnerById(String id) {
		PartnerEntity partnerEntity = findPartnerById(id);
		return createPartnerDTOFromPartnerEntity(partnerEntity);
	}
	
	@Override
	public PartnerDTO createPartner(PartnerRequestDTO partnerRequestDTO) {
		PartnerEntity partnerEntity = PartnerEntity
				.builder()
				.id(generateIdFromName(partnerRequestDTO.getName()))
				.name(partnerRequestDTO.getName())
				.build();
		
		partnerEntity = partnerRepository.save(partnerEntity);
		
		return createPartnerDTOFromPartnerEntity(partnerEntity);
	}
	
	@Override
	public PartnerDTO updatePartner(String id, PartnerRequestDTO partnerRequestDTO) {
		PartnerEntity partnerEntity = findPartnerById(id);
		
		partnerRepository.delete(partnerEntity);
		
		partnerEntity.setId(generateIdFromName(partnerRequestDTO.getName()));
		partnerEntity.setName(partnerRequestDTO.getName());
		
		partnerEntity = partnerRepository.save(partnerEntity);
		
		return createPartnerDTOFromPartnerEntity(partnerEntity);
	}
	
	@Override
	public PartnerEntity findPartnerById(String id) {
		Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findById(id);
		if (partnerEntityOptional.isPresent()) {
			return partnerEntityOptional.get();
		}
		throw new PartnerNotFoundException("Cannot found partnerId: " + id);
	}
	
	@Override
	public void deletePartner(String id) {
		PartnerEntity partnerEntity = findPartnerById(id);
		partnerRepository.delete(partnerEntity);
	}
	
	@Override
	public void createBulkPartners() {
		try {
			File file = new ClassPathResource(partnersFilePath).getFile();
			
			ObjectMapper objectMapper = new ObjectMapper();
			List<PartnerRequestDTO> partnerRequestDTOS = objectMapper.readValue(file, new TypeReference<>() {
			});
			partnerRequestDTOS.forEach(this::createPartner);
			
		} catch (IOException ioException) {
			throw new ReadFileException(partnersFilePath);
		}
	}
	
	private PartnerDTO createPartnerDTOFromPartnerEntity(PartnerEntity partnerEntity) {
		return PartnerDTO.builder()
				.id(partnerEntity.getId())
				.name(partnerEntity.getName())
				.products(partnerEntity.getProducts())
				.build();
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
