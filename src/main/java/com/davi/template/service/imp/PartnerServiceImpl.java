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
		
		return PartnerDTO
				.builder()
				.id(partnerEntity.getId())
				.name(partnerEntity.getName())
				.products(partnerEntity.getProducts())
				.build();
	}
	
	@Override
	public PartnerDTO updatePartner(String id, PartnerRequestDTO partnerRequestDTO) {
		//Emcontrando a entidade trabalhada
		PartnerEntity partnerEntity = findPartnerById(id);
		
		partnerRepository.delete(partnerEntity);
		
		//Modificação e persistencia do objeto
		partnerEntity.setId(generateIdFromName(partnerRequestDTO.getName()));
		partnerEntity.setName(partnerRequestDTO.getName());
		
		partnerEntity = partnerRepository.save(partnerEntity);
		
		//Conversão e retorno do dto
		return PartnerDTO
				.builder()
				.id(partnerEntity.getId())
				.name(partnerEntity.getName())
				.products(partnerEntity.getProducts())
				.build();
		
	}
	
	@Override
	public PartnerEntity findPartnerById(String id) {
		Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findById(id);
		if (partnerEntityOptional.isPresent()) {
			return partnerEntityOptional.get();
		}
		//TODO: CRIAR UMA EXEÇÃO PERSONALIDADA
		throw new RuntimeException("Cannot found partnerId: " + id);
	}
	
	@Override
	public void deletePartner(String id) {
		PartnerEntity partnerEntity = findPartnerById(id);
		partnerRepository.delete(partnerEntity);
	}
	
	@Override
	public void createBulkPartners() {
	}
	
	private PartnerEntity createPartnerDTOFromPartnerEntity(PartnerEntity partnerEntity) {
		return PartnerEntity
				.builder()
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
