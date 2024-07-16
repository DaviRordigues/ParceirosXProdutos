package com.davi.template.repositories;

import com.davi.template.entity.PartnerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

@Repository
public interface PartnerRepository extends MongoRepository<PartnerEntity, String> {
	Optional<PartnerEntity> findByProductsSkuId(String skuId);

	Page<PartnerEntity> findAll(Pageable pageable);
}
