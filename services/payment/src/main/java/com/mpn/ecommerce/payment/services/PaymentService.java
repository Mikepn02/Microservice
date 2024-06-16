package com.mpn.ecommerce.payment.services;

import com.mpn.ecommerce.payment.notification.PaymentNotificationRequest;
import com.mpn.ecommerce.payment.dto.PaymentRequest;
import com.mpn.ecommerce.payment.mapper.PaymentMapper;
import com.mpn.ecommerce.payment.notification.NotificationProducer;
import com.mpn.ecommerce.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository repository;
    private final PaymentMapper mapper;
    private final NotificationProducer notificationProducer;

    public Integer createPayment(PaymentRequest request) {
        var payment = repository.save(mapper.toPayment(request));

        notificationProducer.sendNotification(
                new PaymentNotificationRequest(
                        request.orderReference(),
                        request.amount(),
                        request.paymentMethod(),
                        request.customer().firstname(),
                        request.customer().lastname(),
                        request.customer().email()
                )
        );

        return payment.getId();
    }
}
