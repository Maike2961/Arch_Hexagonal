package io.github.Maike2961.hexagonal.adapters.out.repository;

import io.github.Maike2961.hexagonal.adapters.out.repository.entity.CustomerEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

public interface CustomerRepository extends MongoRepository<CustomerEntity, String> {
}
