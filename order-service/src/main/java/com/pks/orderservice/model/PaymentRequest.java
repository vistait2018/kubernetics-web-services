package com.pks.orderservice.model;

import com.pks.orderservice.enums.PaymentMode;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaymentRequest {
    private Long orderId;
    private PaymentMode paymentMode;
    private String referenceNumber;
    private Instant paymentDate;
    private Long amount;
    private String paymentStatus;
}
