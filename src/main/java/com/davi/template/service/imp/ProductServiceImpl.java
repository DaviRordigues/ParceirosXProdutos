package com.davi.template.service.imp;

import com.davi.template.dtos.PartnerDTO;
import com.davi.template.dtos.ProductDTO;
import com.davi.template.dtos.requests.ProductRequestDTO;
import com.davi.template.entity.PartnerEntity;
import com.davi.template.entity.ProductEntity;
import com.davi.template.repositories.PartnerRepository;
import com.davi.template.service.ProductService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
@Log4j2
public class ProductServiceImpl implements ProductService {
	
	private final PartnerRepository partnerRepository;
	private final Random random = new Random();
	
	@Override
	public List<ProductDTO> getAllProducts(String partnerId) {
		log.info("Fetching all products");
		
		PartnerEntity partnerEntity = findPartnerById(partnerId);
		
		List<ProductEntity> productEntities = partnerEntity.getProducts();
		
		return productEntities.stream().map(productEntity -> {
			return ProductDTO
					.builder()
					.name(productEntity.getName())
					.price(productEntity.getPrice())
					.category(productEntity.getCategory())
					.skuId(productEntity.getSkuId())
					.build();
		}).toList();
	}
	
	
	@Override
	public ProductEntity getProductBySkuId(String skuId) {
		Optional<PartnerEntity> partnerEntity = partnerRepository.findByProductsSkuId(skuId);
		
		if (partnerEntity.isPresent()) {
			PartnerDTO partnerDTO = createDTOFromPartnerEntity(partnerEntity.get());
			return partnerDTO.getProducts().getFirst();
		}
		
		throw new RuntimeException("Cannot found skuId with id: " + skuId);
	}
	
	@Override
	public List<ProductDTO> createProduct(String partnerId, List<ProductRequestDTO> productRequestDTO) {
		PartnerEntity partnerEntity = findPartnerById(partnerId);
		List<ProductEntity> productEntities = createProductEntityFromDTOs(productRequestDTO);
		
		List<ProductEntity> productEntitiesFromEntity = partnerEntity.getProducts();
		
		productEntitiesFromEntity.addAll(productEntities);
		
		partnerEntity.setProducts(productEntitiesFromEntity);
		
		PartnerEntity partner = partnerRepository.save(partnerEntity);
		
		return createProductDTOfromEntityList(partner.getProducts());
	}
	
	@Override
	public ProductDTO updateProduct(String skuId, ProductRequestDTO productRequestDTO) {
		PartnerEntity partnerEntity = findSkuId(skuId);
		ProductEntity productEntity = filterProductBySkuId(skuId, partnerEntity.getProducts());
		List<ProductEntity> productEntities = partnerEntity.getProducts();
		
		productEntities.remove(productEntity);
		
		productEntity.setName(productRequestDTO.getName());
		productEntity.setPrice(productRequestDTO.getPrice());
		
		productEntities.add(productEntity);
		
		partnerEntity.setProducts(productEntities);
		
		partnerRepository.save(partnerEntity);
		
		//TODO Aqui estamos retornando um dto sem passar pela entidade apenas para reduzir a complexidade, porem em um cenário ideal, recuperariamos o produto da entidade
		return ProductDTO
				.builder()
				.name(productRequestDTO.getName())
				.price(productRequestDTO.getPrice())
				.build();
	}
	
	private ProductEntity filterProductBySkuId(String skuId, List<ProductEntity> products) {
		Optional<ProductEntity> optionalProductEntity = products.stream().filter(productEntity -> {
			if (productEntity.getSkuId().equals(skuId)){
				return true;
			}
			else{
				return false;
			}
		}).findFirst();
		
		if(optionalProductEntity.isPresent()){
			return optionalProductEntity.get();
		}
		throw new RuntimeException(String.format("Cannot found skuId with id: %s in seller", skuId));
	}
	
	private PartnerEntity findPartnerById(String partnerId) {
		Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findById(partnerId);
		if (partnerEntityOptional.isPresent()) {
			return partnerEntityOptional.get();
		}
		
		throw new RuntimeException("Cannot found partner with id: " + partnerId);
	}

	private PartnerEntity findSkuId(String skuId) {
		Optional<PartnerEntity> partnerEntityOptional = partnerRepository.findByProductsSkuId(skuId);
		if (partnerEntityOptional.isPresent()){
			return partnerEntityOptional.get();
		}
		throw new RuntimeException("Cannot found skuId with id: " + skuId);
	}
	
	@Override
	public void deleteProduct(String skuId) {
		PartnerEntity partner = findSkuId(skuId);
		ProductEntity productEntity = filterProductBySkuId(skuId, partner.getProducts());
		
		partner.getProducts().remove(productEntity);
		
		partnerRepository.save(partner);
	}
	private List<ProductDTO> createProductDTOfromEntityList(List<ProductEntity> products) {
		return products.stream().map(productEntity -> {
			return createProductDTOFromEntity(productEntity);
		}).toList();
	}
	
	private ProductDTO createProductDTOFromEntity(ProductEntity productEntity) {
		return ProductDTO
				.builder()
				.skuId(productEntity.getSkuId())
				.price(productEntity.getPrice())
				.category(productEntity.getCategory())
				.name(productEntity.getCategory())
				.build();
	}
	
	private List<ProductEntity> createProductEntityFromDTOs(List<ProductRequestDTO> productRequestDTOs) {
		return productRequestDTOs.stream().map(productRequestDTO -> {
			return ProductEntity
					.builder()
					.skuId(generateSkuId())
					.name(productRequestDTO.getName())
					.price(productRequestDTO.getPrice())
					.category(generateCategoryId())
					.build();
		}).toList();
	}
	
	private PartnerDTO createDTOFromPartnerEntity(PartnerEntity partnerEntity) {
		return PartnerDTO
				.builder()
				.id(partnerEntity.getId())
				.name(partnerEntity.getName())
				.products(partnerEntity.getProducts())
				.build();
	}
	
	private String generateSkuId() {
		int number = 100000 + random.nextInt(900000);
		return "SKU" + number;
	}
	
	private String generateCategoryId() {
		StringBuilder categoryId = new StringBuilder("LIV");
		for (int i = 0; i < 5; i++) {
			int nextChar = random.nextInt(36);
			if (nextChar < 10) {
				categoryId.append((char) ('0' + nextChar));
			} else {
				categoryId.append((char) ('A' + nextChar - 10));
			}
		}
		return categoryId.toString();
	}
	
	
}
