package com.pks.ProductService.service;

import com.pks.ProductService.entity.Product;
import com.pks.ProductService.model.ProductRequest;
import com.pks.ProductService.model.ProductResponse;

import java.util.List;

public interface ProductService {
    Long addProduct(ProductRequest productRequest);

    ProductResponse getProductById(Long productId);

    List<ProductResponse> getProducts();

    void reduceProduct(Long productId, Long quantity);
}
