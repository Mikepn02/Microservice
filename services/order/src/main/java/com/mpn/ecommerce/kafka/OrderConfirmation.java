package com.mpn.ecommerce.kafka;

import com.mpn.ecommerce.customer.CustomerResponse;
import com.mpn.ecommerce.order.model.PaymentMethod;
import com.mpn.ecommerce.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {
}
