package com.pks.orderservice.service;



import com.pks.orderservice.entity.Order;
import com.pks.orderservice.enums.PaymentMode;
import com.pks.orderservice.exception.CustomErrorMessage;
import com.pks.orderservice.external.client.PaymentService;
import com.pks.orderservice.external.client.ProductService;
import com.pks.orderservice.model.*;
import com.pks.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ProductService productService;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private PaymentService paymentService;

    @InjectMocks
    OrderService orderService = new OrderServiceImpl();

    @Test
    @DisplayName("Get Order Success Scenario")
    void test_When_Order_Success(){
        //mock
        Order order = getMockOrder();


        when(orderRepository.findById(anyLong()))
                .thenReturn( Optional.of(order));

        when(restTemplate.getForObject(
                "http://PRODUCT-SERVICE/products/"+order.getProductId(),
                ProductResponse.class))
                .thenReturn(getMockProductResponse());
    OrderDetailsResponse  orderDetailsResponse = getMockOrderDetailsResponse();
       when(paymentService
              .getPaymentByOrderId(orderDetailsResponse.getOrderId()))
              .thenReturn( new ResponseEntity<PaymentResponse>(
                      getMockPaymentResponse(),HttpStatus.OK));
     //real
        orderService.getOrderDetails(1l);

        //verification
        verify(orderRepository, times(1)).findById(anyLong());
        verify(restTemplate, times(1)).getForObject(
                "http://PRODUCT-SERVICE/products/"+order.getProductId(),
                ProductResponse.class);
        verify(paymentService, times(1))
                .getPaymentByOrderId(orderDetailsResponse.getOrderId());
        //assert

        Assertions.assertNotNull(orderDetailsResponse);
        Assertions.assertEquals(order.getId(),orderDetailsResponse.getOrderId());
    }

    private ProductResponse getMockProductResponse() {
        return ProductResponse.builder()
                .productId(2L)
                .quantity(90l)
                .productName("Gasket")
                .price(12000l)
                 .build();
    }

    private PaymentResponse getMockPaymentResponse(){
        return PaymentResponse.builder()
                .paymentDate(Instant.now().toString())
                .payment_status("ACCEPTED")
                .referenceNumber(UUID.randomUUID().toString())
                .build();
    }
    private OrderDetailsResponse getMockOrderDetailsResponse(){
        return OrderDetailsResponse.builder()
                .orderId(getMockOrder().getId())
                .quantity(getMockOrder().getQuantity())
                .orderStatus(getMockOrder().getOrderStatus())
                .orderDate(getMockOrder().getOrderDate())
                .amount(getMockOrder().getAmount())
                .paymentResponse(getMockPaymentResponse())
                .productResponse(getMockProductResponse())
                .build();
    }
    private Order getMockOrder(){
        return Order.builder()
                .orderStatus("PLACE")
                .id(1l)
                .amount(12000l)
                .quantity(2l)
                .orderDate(Instant.now())
                .build();

    }


    @Test
    @DisplayName("Get order failure scenario")
    void test_When_Get_Order_Not_Found(){
        //mock
          when(orderRepository.findById(anyLong()))
                  .thenReturn(Optional.ofNullable(null));

          CustomErrorMessage ex = Assertions.assertThrows(
                CustomErrorMessage.class,
                () -> orderService.getOrderDetails(1L));
        //real
        //verify
        Assertions.assertEquals(404,ex.getStatus());
        Assertions.assertEquals("ORDER_NOT_FOUND",ex.getErrorCode());
        //assertions
    verify(orderRepository,times(1))
            .findById(anyLong());
    }

    @Test
    @DisplayName("Place Order - Success")
    void place_order_success(){
        OrderRequest orderRequest = getMockOrderRequest();
        when(productService.reduceQuantity(anyLong(),anyLong())).
        thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        when(orderRepository.save(any(Order.class)))
                .thenReturn(getMockOrder());
        when(paymentService.doPayment(any(PaymentRequest.class)))
        .thenReturn(new ResponseEntity<>(getMockOrder().getId(),HttpStatus.OK));

        Long orderId =orderService.placeOrder(orderRequest);
        verify(orderRepository,times(2))
                .save(any(Order.class));
        verify(productService, times(1))
                .reduceQuantity(anyLong(),anyLong());
        verify(paymentService, times(1))
                .doPayment(any(PaymentRequest.class));
    Assertions.assertEquals(getMockOrder().getId(), orderId);


    }

    @Test
    @DisplayName("When Order is placed and Payment fails but order is placed")
    void when_Place_Order_Payment_Fails_then_Order_is_Placed(){
        OrderRequest orderRequest = getMockOrderRequest();
        when(productService.reduceQuantity(anyLong(),anyLong())).
                thenReturn(new ResponseEntity<Void>(HttpStatus.OK));
        when(orderRepository.save(any(Order.class)))
                .thenReturn(getMockOrder());
        when(paymentService.doPayment(any(PaymentRequest.class)))
                .thenThrow(new RuntimeException());

        Long orderId =orderService.placeOrder(orderRequest);

        verify(orderRepository,times(2))
                .save(any(Order.class));
        verify(productService, times(1))
                .reduceQuantity(anyLong(),anyLong());
        verify(paymentService, times(1))
                .doPayment(any(PaymentRequest.class));
        Assertions.assertEquals(getMockOrder().getId(), orderId);

    }
    private OrderRequest getMockOrderRequest(){
       return OrderRequest.builder()
               .paymentMode(PaymentMode.CASH)
               .productId(2l)
               .quantity(4l)
               .totalAmount(4000l)
                .build();
    }

    private PaymentRequest getMockOrderPaymentRequest(){
        return PaymentRequest.builder()
                .amount(getMockOrderRequest().getTotalAmount())
                .paymentMode(getMockOrderRequest().getPaymentMode())
                .orderId(getMockOrderPaymentRequest().getOrderId())
                .build();
    }

}