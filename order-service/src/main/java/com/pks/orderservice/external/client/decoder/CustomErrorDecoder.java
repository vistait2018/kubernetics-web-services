package com.pks.orderservice.external.client.decoder;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pks.orderservice.exception.CustomErrorMessage;
import com.pks.orderservice.external.client.decoder.response.ErrorMessage;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;

@Log4j2
public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        ObjectMapper objectMapper =
                new ObjectMapper();
        log.info("::{}",response.request().url());
        log.info("::{}",response.request().headers());
        try {
            ErrorMessage errorMessage
                    = objectMapper.readValue(
                    response.body().asInputStream(),
                    ErrorMessage.class
            );

            return new CustomErrorMessage(
                    errorMessage.getErrorMessage(),
                    errorMessage.getErrorCode(),
                    response.status()
            );
        }catch (StreamReadException e) {
            throw new CustomErrorMessage(
                    "Internal Server Error",
                    "Internal Server Error",
                    500
            );
        } catch (DatabindException e) {
            throw new CustomErrorMessage(
                    "Internal Server Error",
                    "Internal Server Error",
                    500
            );
        } catch (IOException e) {
            throw new CustomErrorMessage(
                    "Internal Server Error",
                    "Internal Server Error",
                    500
            );
        }

    }
}
