package com.pks.paymentservice.service;


import com.pks.paymentservice.entity.TransactionDetails;
import com.pks.paymentservice.external.client.decoder.CustomErrorMessage;
import com.pks.paymentservice.model.PaymentRequest;
import com.pks.paymentservice.model.PaymentResponse;
import com.pks.paymentservice.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Log4j2
@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;
    @Override
    public Long doPayment(PaymentRequest paymentRequest) {
   log.info("entering payment info -{} " ,paymentRequest);
        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .amount(paymentRequest.getAmount())
                .paymentStatus("SUCCESS")
                .paymentMode(String.valueOf(paymentRequest.getPaymentMode()))
                .referenceNumber(paymentRequest.getReferenceNumber())
                .orderId(paymentRequest.getOrderId())
                .build();
         transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction completed with id -{} ",transactionDetails.getId());
     return transactionDetails.getId();

    }

    @Override
    public PaymentResponse getPaymentByOrderId(Long orderId) {
        log.info("Acquiring Transaction with orderId -{} ",orderId);
       TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId)
               .orElseThrow(()->new CustomErrorMessage(
                       "Transaction with id - "+ orderId+ " does not exist",
                       "TRANSACTION_WITH_ID_DOES_NOT_EXIST",
                       404
               ));
       PaymentResponse paymentResponse = PaymentResponse
               .builder()
               .payment_status(transactionDetails.getPaymentStatus())
               .referenceNumber(transactionDetails.getReferenceNumber())
               .paymentDate(transactionDetails.getPaymentDate().toString())
               .paymentStatus(transactionDetails.getPaymentStatus())
               .paymentId(transactionDetails.getId())
               .build();
        log.info(" Transaction acquired -{} ",paymentResponse);
       return  paymentResponse;
    }
}
