package com.example.itemservice.service;

import com.example.itemservice.domain.model.JwtAuthenticationResponse;

import java.util.List;
import java.util.Optional;

public interface TokenBlackListService {

    List<JwtAuthenticationResponse> findAll();

    JwtAuthenticationResponse add(JwtAuthenticationResponse jwtAuthenticationResponse);

    Optional<JwtAuthenticationResponse> findByToken(String token);
}
