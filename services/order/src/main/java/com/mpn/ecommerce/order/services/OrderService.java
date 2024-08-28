package com.mpn.ecommerce.order.services;

import com.mpn.ecommerce.customer.CustomerClient;
import com.mpn.ecommerce.kafka.OrderConfirmation;
import com.mpn.ecommerce.kafka.OrderProducer;
import com.mpn.ecommerce.order.dto.OrderLineRequest;
import com.mpn.ecommerce.order.dto.OrderRequest;
import com.mpn.ecommerce.order.dto.OrderResponse;
import com.mpn.ecommerce.order.exception.BusinessException;
import com.mpn.ecommerce.order.mapper.OrderMapper;
import com.mpn.ecommerce.order.repository.OrderRepository;
import com.mpn.ecommerce.payment.PaymentClient;
import com.mpn.ecommerce.payment.PaymentRequest;
import com.mpn.ecommerce.product.ProductClient;
import com.mpn.ecommerce.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final CustomerClient customerClient;
    private final ProductClient productClient;
    private final OrderRepository repository;
    private final OrderLineService orderLineService;
    private final OrderMapper mapper;
    private final PaymentClient paymentClient;
    private final OrderProducer orderProducer;

    public Integer createOrder(OrderRequest request) {

        var customer = this.customerClient.findCustomerById(request.customerId())
                .orElseThrow(() -> new BusinessException("Can create order :: No customer exist with the provided id"));

        var purchasedProduct = this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));

        for (PurchaseRequest purchaseRequest : request.products()) {
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }
        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer

        );
        paymentClient.requestOrderPayment(paymentRequest);
        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        customer,
                        purchasedProduct

                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAllOrders() {
        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No Order found with the provided id: %d", orderId)));
    }
}
