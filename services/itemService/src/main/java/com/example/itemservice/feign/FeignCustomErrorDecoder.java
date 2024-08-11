package com.example.itemservice.feign;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FeignCustomErrorDecoder implements ErrorDecoder {

    @Override public Exception decode(String methodKey, Response response) {

        /*START DECODING ORIGINAL ERROR MESSAGE*/
        String erroMessage = null;
        Reader reader = null;

        /*capturing error message from response body.*/
        try {
            reader = response.body().asReader(StandardCharsets.UTF_8);
            String result = IOUtils.toString(reader);
            ObjectMapper mapper = new ObjectMapper();
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            FeignExceptionMessage exceptionMessage = mapper.readValue(result,
                    FeignExceptionMessage.class);

            erroMessage = exceptionMessage.getMessage();

        } catch (IOException e) {
            log.error("IO Exception on reading exception message feign client" + e);
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error("IO Exception on reading exception message feign client" + e);
            }
        }

        /*END DECODING ORIGINAL ERROR MESSAGE*/

        return switch (response.status()) {
            case 400 -> {
                log.error("Error in request went through feign client {} ", erroMessage);
                yield new Exception("Bad Request Through Feign");
                /*handle exception*/
            }
            case 401 -> {
                log.error("Error in request went through feign client {} ", erroMessage);
                yield new Exception("Unauthorized Request Through Feign");
                /*handle exception*/
            }
            case 404 -> {
                log.error("Error in request went through feign client {} ", erroMessage);
                yield new Exception("Unidentified Request Through Feign");
                /*handle exception*/
            }
            default -> {
                log.error("Error in request went through feign client {} ", erroMessage);
                yield new Exception("Common Feign Exception");
                /*handle exception*/
            }
        };
    }

}
