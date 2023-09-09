package com.pks.orderservice.exception;

import lombok.Data;

@Data
public class CustomErrorMessage extends RuntimeException{
    private String errorCode;
    private int status;

    public CustomErrorMessage(String message, String errorCode, int status) {
       super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
}
