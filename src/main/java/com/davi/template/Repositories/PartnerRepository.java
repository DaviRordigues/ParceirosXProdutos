package com.davi.template.Repositories;

import com.davi.template.Entity.PartnerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends MongoRepository<PartnerEntity, String> {
}
