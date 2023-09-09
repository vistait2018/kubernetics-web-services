package com.pks.ProductService.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorMessage {
    private HttpStatus status;
    private String errorMessage;
    private String errorCode;


}
