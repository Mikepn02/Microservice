package com.mpn.ecommerce.customer.repository;

import com.mpn.ecommerce.customer.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<Customer, String> {
}
