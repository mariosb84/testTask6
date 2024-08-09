package com.example.itemservice.feign;

import lombok.Data;

@Data
public class Airline {
    private Long id;
    private String name;
    private String country;
    private String logo;
    private String slogan;
    private String headQuaters;
    private String website;
    private String established;
}