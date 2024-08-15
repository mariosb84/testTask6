package com.example.itemservice.feign.config;

import com.example.itemservice.feign.FeignCustomErrorDecoder;
import feign.Logger;
import feign.RequestInterceptor;
import feign.codec.ErrorDecoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomFeignClientConfiguration {

    @Value("${app.aPiDaDataToken}")
    private String aPiDaDataToken;

    @Value("${app.aPiDaDataSecret}")
    private String aPiDaDataSecret;

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Authorization", aPiDaDataToken);
            requestTemplate.header("X-Secret", aPiDaDataSecret);
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
        };
    }

    @Bean
    public SpringEncoder encoder(ObjectFactory<HttpMessageConverters> converters) {
        return new SpringEncoder(converters);
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new FeignCustomErrorDecoder();
    }

}
