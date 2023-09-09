package com.pks.paymentservice.service;


import com.pks.paymentservice.model.PaymentRequest;
import com.pks.paymentservice.model.PaymentResponse;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentByOrderId(Long orderId);
}
