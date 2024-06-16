package com.mpn.ecommerce.payment;

import com.mpn.ecommerce.customer.CustomerResponse;
import com.mpn.ecommerce.order.model.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
