package com.pks.orderservice.service;

import com.pks.orderservice.entity.Order;
import com.pks.orderservice.enums.OrderStatus;
import com.pks.orderservice.exception.CustomErrorMessage;
import com.pks.orderservice.external.client.PaymentService;
import com.pks.orderservice.external.client.ProductService;
import com.pks.orderservice.model.*;
import com.pks.orderservice.repository.OrderRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.UUID;

@Service
@Log4j2
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductService productService;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PaymentService paymentService;



    @Override
    public Long placeOrder(OrderRequest orderRequest) {
        log.info("Placing order - {}",orderRequest);

        productService.reduceQuantity(orderRequest.getProductId()
                ,orderRequest.getQuantity());
       Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderDate(Instant.now())
                .orderStatus(OrderStatus.COMPLETED.toString())
                .productId(orderRequest.getProductId())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);
        log.info("Calling payment Service to complete payment on order- {} ",order);
          PaymentRequest paymentRequest
                  = PaymentRequest.builder()
                  .orderId(order.getId())
                  .paymentMode(orderRequest.getPaymentMode())
                  .amount(orderRequest.getTotalAmount())
                  .paymentDate(Instant.now())
                  .referenceNumber(UUID.randomUUID().toString())
                  .build();
          String orderStatus = null;
          try{
              paymentService.doPayment(paymentRequest);
              log.info("Payment Successful, changing " +
                      "order status to PLACED");
              orderStatus = "PLACED";
          }catch(Exception ex){
              log.info("Payment failed");
              orderStatus = "PAYMENT_FAILED";
          }
          order.setOrderStatus(orderStatus);
        order = orderRepository.save(order);
        log.info("Order placed successfully " +
                "with with product Id -{}- Payment Status ", order.getId(), orderStatus);

        return order.getId();
    }

    @Override
    public OrderDetailsResponse getOrderDetails(Long orderId) {
      log.info("saerching for order with Id -{} ",orderId);
        Order order = orderRepository.findById(orderId)
               .orElseThrow(()-> new CustomErrorMessage(
                 "order with id "+orderId+" not found",
                 "ORDER_NOT_FOUND",
                 404
               ));
        log.info("acquired order -{} ",order);
      OrderDetailsResponse orderDetailsResponse = OrderDetailsResponse.builder()
              .amount(order.getAmount())
              .orderDate(order.getOrderDate())
              .orderStatus(order.getOrderStatus())
              .orderId(orderId)
              .quantity(order.getQuantity())
              .build();
        ProductResponse productResponse = null;
                try{
                    //the feign resouce is ready just implement restTemplate
                    productResponse =restTemplate.getForObject(
                            "http://PRODUCT-SERVICE/products/"+order.getProductId(),
                            ProductResponse.class)  ;
                    log.info("acquired products on productId -{} ",order.getProductId());
                }catch (Exception ex ){
                     productResponse = null;
                }
        PaymentResponse paymentResponse = null;
                try{

                   paymentResponse= paymentService
                            .getPaymentByOrderId(orderDetailsResponse.getOrderId()).getBody();
                    log.info("acquired Transaction on  -{} ",orderDetailsResponse);
                }
                catch (Exception ex ){
                    paymentResponse = null;

                }
        orderDetailsResponse.setPaymentResponse(paymentResponse);
                orderDetailsResponse.setProductResponse(productResponse);


      return orderDetailsResponse;

    }
}
