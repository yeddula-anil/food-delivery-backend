package org.example.food.service;

import org.example.food.model.OrderItem;
import org.example.food.response.PaymentResponse;

public interface PaymentService {
    public PaymentResponse createPaymentLink(OrderItem orderItem);
}
