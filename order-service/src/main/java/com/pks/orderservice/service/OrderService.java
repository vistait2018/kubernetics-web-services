package com.pks.orderservice.service;

import com.pks.orderservice.model.OrderDetailsResponse;
import com.pks.orderservice.model.OrderRequest;

public interface OrderService {
    Long placeOrder(OrderRequest orderRequest);

    OrderDetailsResponse getOrderDetails(Long orderId);
}
