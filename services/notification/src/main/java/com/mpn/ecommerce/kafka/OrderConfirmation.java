package com.mpn.ecommerce.kafka;

import com.mpn.ecommerce.kafka.order.Customer;
import com.mpn.ecommerce.kafka.order.Product;
import com.mpn.ecommerce.kafka.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        Customer customer,
        List<Product> products
) {
}
