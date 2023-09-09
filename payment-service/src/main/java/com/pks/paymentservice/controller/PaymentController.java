package com.pks.paymentservice.controller;

import com.pks.paymentservice.model.PaymentRequest;
import com.pks.paymentservice.model.PaymentResponse;
import com.pks.paymentservice.service.PaymentService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@Log4j2
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/do-payment")
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest)
    {
        log.info("entering payment in the controller -{} ",paymentRequest);
      return new ResponseEntity<>(paymentService.doPayment(paymentRequest), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<PaymentResponse> getPaymentByOrderId(
            @PathVariable("orderId") Long orderId){
        return new ResponseEntity<>(paymentService.getPaymentByOrderId(orderId), HttpStatus.OK);

    }
}
