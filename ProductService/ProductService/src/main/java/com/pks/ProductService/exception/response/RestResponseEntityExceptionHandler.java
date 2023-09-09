package com.pks.ProductService.exception.response;

import com.pks.ProductService.exception.ErrorMessage;
import com.pks.ProductService.exception.ProductNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ProductNotFoundException.class)
     public ResponseEntity<ErrorMessage> productNotFoundHandler(
            ProductNotFoundException ex) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .errorCode(ex.getErrorCode())
                .errorMessage(ex.getErrorCode())
                .build(),HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessage> genericExceptionHandler(
            Exception ex) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .errorCode("INTERNAL_SERVER_ERROR")
                .errorMessage(ex.getMessage())
                .build(),HttpStatus.INTERNAL_SERVER_ERROR );

    }
}
