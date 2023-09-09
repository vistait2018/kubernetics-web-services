//package com.pks.orderservice.controller;
//
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
//import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
//import com.pks.orderservice.config.OrderServiceConfig;
//import com.pks.orderservice.entity.Order;
//import com.pks.orderservice.enums.PaymentMode;
//import com.pks.orderservice.model.OrderRequest;
//import com.pks.orderservice.model.PaymentRequest;
//import com.pks.orderservice.repository.OrderRepository;
//import com.pks.orderservice.service.OrderService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.RegisterExtension;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//import org.springframework.util.StreamUtils;
//
//
//import java.io.IOException;
//import java.nio.charset.Charset;
//import java.util.Optional;
//
//import static com.github.tomakehurst.wiremock.client.WireMock.*;
//
//
//
//@SpringBootTest({"server.port=0"})
//@EnableConfigurationProperties
//@AutoConfigureMockMvc
//@ContextConfiguration(classes={OrderServiceConfig.class})
public class OrderControllerTest {
//
//    @Autowired
//    private OrderService orderService;
//
//    @Autowired
//    private OrderRepository orderRepository;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @RegisterExtension
//    static WireMockExtension wireMockExtension
//            = WireMockExtension.newInstance()
//            .options(WireMockConfiguration
//                    .wireMockConfig()
//                    .port(8080))
//            .build();
//
//    private ObjectMapper objectMapper =
//            new ObjectMapper()
//                    .findAndRegisterModules()
//                    .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false)
//                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
//    @BeforeEach
//    void setUp() throws IOException {
//       getProductDetailsResponse();
//       doPayment();
//       getPaymentDetail();
//       reduceQuantity();
//    }
//
//    private void reduceQuantity() {
//        // PUT /products/reduce-quantity/{id}
//        wireMockExtension.stubFor(post(urlMatching("products/reduce-quantity/.*"))
//                .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON)
//                        )));
//    }
//
//    private void getPaymentDetail() throws IOException {
//        // GET /payment/.*
//        wireMockExtension.stubFor(get(urlMatching("/payment/.*"))
//                .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
//                        .withBody(StreamUtils.copyToString(
//                                OrderControllerTest.class
//                                        .getClassLoader()
//                                        .getResourceAsStream(
//                                                "mock/getPayment.json"
//                                        ), Charset.defaultCharset()
//                        ))));
//    }
//
//    private void doPayment() {
//        // POST /payments/do-payment
//        wireMockExtension.stubFor(post(urlEqualTo("/payments/do-payment"))
//                .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON)
//                        )));
//    }
//
//    private void getProductDetailsResponse() throws IOException {
//        //   GET/"product/1"
//        wireMockExtension.stubFor(get("/products/1")
//                .willReturn(aResponse()
//                        .withStatus(HttpStatus.OK.value())
//                        .withHeader("Content-Type", String.valueOf(MediaType.APPLICATION_JSON))
//                        .withBody(StreamUtils.copyToString(
//                                OrderControllerTest.class
//                                        .getClassLoader()
//                                        .getResourceAsStream(
//                                                "mock/productDetails.json"
//                                        ), Charset.defaultCharset()
//                        ))));
//
//    }
//
//       @Test
//       void test_WhenPlaceOrderDoPayment() throws Exception {
////        //place order
////        OrderRequest orderRequest = getMockOrderRequest();
////       MvcResult mvcResult =mockMvc.perform(MockMvcRequestBuilders.post("/payments/do-payment")
////       .with(SecurityMockMvcRequestPostProcessors.jwt()
////               .authorities(new SimpleGrantedAuthority("customer")))
////               .content(MediaType.APPLICATION_JSON_VALUE)
////                       .content(objectMapper.writeValueAsString(orderRequest))
////                    ).andExpect(MockMvcResultMatchers.status().isOk())
////               .andReturn();
////          Long oId =orderService.placeOrder(orderRequest);
////        // get Order by Id from Db
////        final String orderId = mvcResult.getResponse().getContentAsString();
////        Optional<Order>  order = orderRepository.findById(Long.valueOf(orderId));
////        // check output
////        Assertions.assertTrue(order.isPresent()) ;
////        Order o = order.get();
////        Assertions.assertEquals(Long.valueOf(orderId),o.getId());
////        Assertions.assertEquals("PLACED",o.getOrderStatus());
////        Assertions.assertEquals(orderRequest.getTotalAmount(),o.getAmount());
////        Assertions.assertEquals(orderRequest.getQuantity(),o.getQuantity());
//
//}
//
//
//    private OrderRequest getMockOrderRequest(){
//        return OrderRequest.builder()
//                .paymentMode(PaymentMode.CASH)
//                .productId(2l)
//                .quantity(4l)
//                .totalAmount(4000l)
//                .build();
//    }
//
//    private PaymentRequest getMockOrderPaymentRequest(){
//        return PaymentRequest.builder()
//                .amount(getMockOrderRequest().getTotalAmount())
//                .paymentMode(getMockOrderRequest().getPaymentMode())
//                .orderId(getMockOrderPaymentRequest().getOrderId())
//                .build();
//    }
}