package com.example.itemservice.feign.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PhoneDataDto {

    /*Код города / DEF-код*/
    @Size(max = 5)
    private String cityCode;

    /*Код страны*/
    @Size(max = 5)
    private String countryCode;

    /*Стандартизованный телефон одной строкой*/
    @Size(max = 50)
    private String phoneNumber;

}
