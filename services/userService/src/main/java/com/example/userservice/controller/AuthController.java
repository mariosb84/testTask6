package com.example.userservice.controller;

import com.example.userservice.domain.dto.JwtAuthenticationResponseDto;
import com.example.userservice.domain.dto.SignInRequest;
import com.example.userservice.domain.dto.SignUpRequest;
import com.example.userservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public JwtAuthenticationResponseDto signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDto signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/auth_logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        authenticationService.logout(new JwtAuthenticationResponseDto(token));
        return ResponseEntity.ok("Logged out successfully!");
    }

}
