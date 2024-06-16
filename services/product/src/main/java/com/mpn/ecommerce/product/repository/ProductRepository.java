package com.mpn.ecommerce.product.repository;

import com.mpn.ecommerce.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product , Integer> {

    List<Product> findAllByIdInOrderById(List<Integer> productIds);
}
