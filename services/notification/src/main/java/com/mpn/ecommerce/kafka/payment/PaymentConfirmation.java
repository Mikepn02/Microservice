package com.mpn.ecommerce.kafka.payment;

import com.mpn.ecommerce.kafka.order.Product;

import java.math.BigDecimal;
import java.util.List;

public record PaymentConfirmation(
       String orderReference,
       List<Product> products,
       PaymentMethod paymentMethod,
       String customerFirstName,
       String customerLastName,
       String customerEmail
) {
}
