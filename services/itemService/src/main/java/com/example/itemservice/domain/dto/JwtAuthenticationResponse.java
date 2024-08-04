package com.example.itemservice.domain.dto;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "token_black_list")
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
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
