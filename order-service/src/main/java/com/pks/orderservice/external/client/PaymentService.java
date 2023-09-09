package com.pks.orderservice.external.client;

import com.pks.orderservice.exception.CustomErrorMessage;
import com.pks.orderservice.model.PaymentRequest;
import com.pks.orderservice.model.PaymentResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@CircuitBreaker(name="external", fallbackMethod = "fallback")
@FeignClient(name="PAYMENT-SERVICE/payments")
public interface PaymentService {
   @PostMapping("/do-payment")
   ResponseEntity<Long> doPayment
            (@RequestBody PaymentRequest paymentRequest);

   @GetMapping("/{orderId}")
   public ResponseEntity<PaymentResponse> getPaymentByOrderId(
           @PathVariable("orderId") Long orderId);

   default void fallback(){
      throw new CustomErrorMessage("Payment Service is not Available",
              "PRODUCT_NOT_AVAILABLE",
              500);
   }
}
