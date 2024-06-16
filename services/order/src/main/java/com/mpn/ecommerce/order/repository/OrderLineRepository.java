package com.mpn.ecommerce.order.repository;


import com.mpn.ecommerce.order.model.OrderLine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderLineRepository extends JpaRepository<OrderLine , Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
