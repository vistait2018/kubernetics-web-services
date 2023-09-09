package com.pks.ProductService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

   @Column(name="PRODUCT_NAME")
    private String productName;

    @Column(name="PRICE")
    private Long price;

    @Column(name="QUANTITY")
    private Long quantity;
}
