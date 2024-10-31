package com.example.userservice.service;

import com.example.userservice.domain.model.JwtAuthenticationResponse;

import java.util.List;
import java.util.Optional;

public interface TokenBlackListService {

    List<JwtAuthenticationResponse> findAll();

    JwtAuthenticationResponse add(JwtAuthenticationResponse jwtAuthenticationResponse);

    Optional<JwtAuthenticationResponse> findByToken(String token);
}
