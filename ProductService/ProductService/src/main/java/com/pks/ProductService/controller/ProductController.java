package com.pks.ProductService.controller;

import com.pks.ProductService.model.ProductRequest;
import com.pks.ProductService.model.ProductResponse;
import com.pks.ProductService.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PreAuthorize("hasAuthority('admin')")
    @PostMapping
    public ResponseEntity<Long> addProduct(@RequestBody ProductRequest productRequest){
        //System.out.println(productRequest);
      Long productId = productService.addProduct(productRequest);
      return new ResponseEntity<>(productId, HttpStatus.CREATED);
    }


    @PutMapping("/reduce-quantity/{id}")
    public ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") Long productId,
            @RequestParam Long quantity ){
        productService.reduceProduct(productId,quantity);
        return new ResponseEntity<>(HttpStatus.OK);


    }

    @PreAuthorize("hasAuthority('admin') || hasAuthority('customer') || hasAuthority('SCOPE_')")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable("id") Long productId) {

            ProductResponse productResponse = productService.getProductById(productId);
            return new ResponseEntity<>(productResponse, HttpStatus.OK);

        }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts () {
           return new ResponseEntity<>(productService.getProducts(), HttpStatus.OK) ;

    }


}
