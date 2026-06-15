package com.joao.hexagonal_architecture_introduction_project.adapters.out.repository;

import com.joao.hexagonal_architecture_introduction_project.adapters.out.repository.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
}
