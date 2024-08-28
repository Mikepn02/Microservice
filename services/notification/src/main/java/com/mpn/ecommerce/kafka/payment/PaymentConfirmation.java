package com.mpn.ecommerce.kafka.payment;

import com.mpn.ecommerce.kafka.order.Product;

import java.math.BigDecimal;


public record PaymentConfirmation(
       String orderReference,
       BigDecimal amount,
       PaymentMethod paymentMethod,
       String customerFirstName,
       String customerLastName,
       String customerEmail
) {
}
