package com.pks.orderservice.controller;

import com.pks.orderservice.entity.Order;
import com.pks.orderservice.external.client.ProductService;
import com.pks.orderservice.model.OrderDetailsResponse;
import com.pks.orderservice.model.OrderRequest;
import com.pks.orderservice.service.OrderService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('admin') ")
     @PostMapping("/place-orders")
    private ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
      Long orderId = orderService.placeOrder(orderRequest);
      log.info("Order Id is {} - ", orderId);
      return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('admin') || hasAuthority('customer') ")
    @PostMapping("/get-order-details/{id}")
    public ResponseEntity<OrderDetailsResponse> getOrderDetails(@PathVariable("id")  Long orderId){
       log.info("Acquiring orders with id  - {} ",orderId) ;
       return new ResponseEntity<>(orderService.getOrderDetails(orderId),HttpStatus.OK);
    }


}
