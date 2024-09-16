package com.example.itemservice.domain.dto;

import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor

public class UserDto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private long id;

    @NonNull
    private String password;

}
