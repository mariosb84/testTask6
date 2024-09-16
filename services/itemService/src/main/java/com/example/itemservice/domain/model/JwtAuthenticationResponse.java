package com.example.itemservice.domain.model;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@Entity
@Table(name = "token_black_list")
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    @Column(name = "token_id")
    private long id;

    @NonNull
    @Column(name = "token_name")
    private String token;

}
