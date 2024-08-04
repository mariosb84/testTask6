package com.example.itemservice.service;

import com.example.itemservice.domain.dto.JwtAuthenticationResponse;
import com.example.itemservice.repository.TokenBlackListRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenBlackListServiceData implements TokenBlackListService {

    private TokenBlackListRepository tokenBlackListRepository;

    @Override
    public List<JwtAuthenticationResponse> findAll() {
        return tokenBlackListRepository.findAll();
    }

    @Override
    public JwtAuthenticationResponse add(JwtAuthenticationResponse jwtAuthenticationResponse) {
        return tokenBlackListRepository.save(jwtAuthenticationResponse);
    }

    @Override
    public Optional<JwtAuthenticationResponse> findByToken(String token) {
        return tokenBlackListRepository.findByToken(token);
    }

}
