package com.mpn.ecommerce.payment.mapper;

import com.mpn.ecommerce.payment.dto.PaymentRequest;
import com.mpn.ecommerce.payment.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest request) {
        if (request == null) {
            return null;
        }
        return Payment.builder()
                .id(request.id())
                .paymentMethod(request.paymentMethod())
                .amount(request.amount())
                .orderId(request.orderId())
                .build();
    }
}