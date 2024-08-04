package com.example.itemservice.repository;


import com.example.itemservice.domain.dto.JwtAuthenticationResponse;
import lombok.NonNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenBlackListRepository extends CrudRepository<JwtAuthenticationResponse, Long> {

    @NonNull
    List<JwtAuthenticationResponse> findAll();

    Optional<JwtAuthenticationResponse> findByToken(String token);



}
