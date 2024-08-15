package com.example.itemservice.feign;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonSerialize(using = PhoneSourceSerializer.class)
public class PhoneSource {

    /*Исходный телефон одной строкой*/
    @Size(max = 100)
    private List<String> source;

}
