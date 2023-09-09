package com.pks.ProductService.service;

import com.pks.ProductService.entity.Product;
import com.pks.ProductService.exception.ProductNotFoundException;
import com.pks.ProductService.model.ProductRequest;
import com.pks.ProductService.model.ProductResponse;
import com.pks.ProductService.repository.ProductRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Long addProduct(ProductRequest productRequest) {
      log.info("Adding Products.....");
        System.out.println(productRequest);
      Product product =
              Product.builder()
                      .price(productRequest.getPrice())
                      .productName(productRequest.getName())
                      .quantity(productRequest.getQuantity())
                      .build();
      Product savedProduct =productRepository.save(product);
      log.info("Product created.");
      return savedProduct.getProductId();

    }

    @Override
    public ProductResponse getProductById(Long productId) {
        log.info("Getting Product By Id...");
        Product product = productRepository
                .findById(productId)
                .orElseThrow(()->
                        new ProductNotFoundException("Product with Id - "+ productId
                                +" not found","PRODUCT_NOT_FOUND")
                );
        log.info("Product with Id {} - acquired...", productId);
       return    ProductResponse.builder()
                   .productId(product.getProductId())
                   .price(product.getPrice())
                   .productName(product.getProductName())
                   .quantity(product.getQuantity())
           .build();
    }

    @Override
    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
       for(Product p : products){
           ProductResponse response = new ProductResponse();
           response.setProductId(p.getProductId());
           response.setProductName(p.getProductName());
           response.setPrice(p.getPrice());
           response.setQuantity(p.getQuantity());
           productResponses.add(response);
       }
        return productResponses ;
    }

    @Override
    public void reduceProduct(Long productId, Long quantity) {
        log.info("reducing quantity -ProductId: {} and quantity {}",productId,quantity);
        Long newQuantity = 0L;
        Product productToReduce = productRepository.findById(productId)
                .orElseThrow(()->
                        new ProductNotFoundException("Product with Id - "+ productId
                                +" not found","PRODUCT_NOT_FOUND"));
        if(productToReduce.getQuantity() == 0){
            throw new ProductNotFoundException("Product not available" +
                    "", "INSUFFICIENT_QUANTITY");
        }
        if(productToReduce.getQuantity() -quantity   < 0){
            throw new ProductNotFoundException("Quantity available is too little for " +
                    "the required transaction - try reducing the quantity" +
                    "", "INSUFFICIENT_QUANTITY");
        }

        productToReduce.setQuantity(productToReduce.getQuantity()-quantity );
        productRepository.save(productToReduce);


    }
}
