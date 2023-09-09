package com.pks.ProductService.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductNotFoundException extends RuntimeException{
    private String errorCode;
    public ProductNotFoundException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }


}
