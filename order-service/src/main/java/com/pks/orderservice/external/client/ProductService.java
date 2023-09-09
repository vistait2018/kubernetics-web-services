package com.pks.orderservice.external.client;

import com.pks.orderservice.exception.CustomErrorMessage;
import com.pks.orderservice.model.ProductResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CircuitBreaker(name="external", fallbackMethod = "fallback")
@FeignClient(name="PRODUCT-SERVICE/products")
public interface ProductService {
    @PutMapping("/reduce-quantity/{id}")
     ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") Long productId,
            @RequestParam Long quantity );

    @GetMapping("/{id}")
    ResponseEntity<ProductResponse> getProductById(
            @PathVariable("id") Long productId);

    default void fallback(){
        throw new CustomErrorMessage("Product Service is not Available",
                "PRODUCT_NOT_AVAILABLE",
                500);
    }

}
