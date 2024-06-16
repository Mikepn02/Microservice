package com.mpn.ecommerce.order.repository;

import com.mpn.ecommerce.order.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order , Integer> {
}
