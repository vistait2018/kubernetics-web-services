package com.pks.paymentservice.model;

import com.pks.paymentservice.enums.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {
    private Long orderId;
    private PaymentMode paymentMode;
    private String referenceNumber;
    private Long amount;
}
