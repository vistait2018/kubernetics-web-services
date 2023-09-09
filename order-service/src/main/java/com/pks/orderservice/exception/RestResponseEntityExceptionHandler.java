package com.pks.orderservice.exception;

import com.pks.orderservice.external.client.decoder.response.ErrorMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Log4j2
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(CustomErrorMessage.class)
     public ResponseEntity<ErrorMessage> customErrorHandler(
            CustomErrorMessage ex) {
        return new ResponseEntity<>(ErrorMessage.builder()
                .errorCode(ex.getErrorCode())
                .status(HttpStatus.NOT_FOUND)
                .errorMessage(ex.getErrorCode())
                .build(),HttpStatus.valueOf(ex.getStatus()));

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
