package com.pks.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailsResponse {
     private Long orderId;
     private  Long amount;
     private Instant orderDate;
     private String orderStatus;
     private Long quantity;
    private ProductResponse productResponse;
     private PaymentResponse paymentResponse;



}
