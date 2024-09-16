package com.example.itemservice.feign.domain.model;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class PhoneData {

    /*Исходный телефон одной строкой*/
    @Size(max = 100)
    private String source;

    /*Тип телефона*/
    @Size(max = 50)
    private String type;

    /*Стандартизованный телефон одной строкой*/
    @Size(max = 50)
    private String phone;

    /*Код страны*/
    @Size(max = 5)
    private String country_code;

    /*Код города / DEF-код*/
    @Size(max = 5)
    private String city_code;

    /*Локальный номер телефона*/
    @Size(max = 10)
    private String number;

    /*Добавочный номер*/
    @Size(max = 10)
    private String extension;

    /*Оператор связи (только для России)*/
    @Size(max = 100)
    private String provider;

    /*Страна*/
    @Size(max = 50)
    private String country;

    /*Регион (только для России)*/
    @Size(max = 100)
    private String region;

    /*Город (только для стационарных телефонов)*/
    @Size(max = 100)
    private String city;

    /*Часовой пояс города для России,
     часовой пояс страны — для
     иностранных телефонов.
     Если у страны несколько поясов, вернёт
     минимальный и максимальный через слеш:
     UTC+5/UTC+6*/
    @Size(max = 50)
    private String timezone;

    /* Признак конфликта телефона с адресом*/
    @Size(max = 5)
    private String qc_conflict;

    /*Код проверки*/
    @Size(max = 5)
    private String qc;

}
