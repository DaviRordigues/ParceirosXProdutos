package com.davi.template.repositories;

import com.davi.template.entity.PartnerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends MongoRepository<PartnerEntity, String> {
}
