package com.example.itemservice.controller;

import com.example.itemservice.domain.dto.JwtAuthenticationResponse;
import com.example.itemservice.domain.dto.SignInRequest;
import com.example.itemservice.domain.dto.SignUpRequest;
import com.example.itemservice.domain.model.Item;
import com.example.itemservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
/*@Tag(name = "Аутентификация")*/
public class AuthController {
    private final AuthenticationService authenticationService;

    /*@Operation(summary = "Регистрация пользователя")*/
    @PostMapping("/sign-up")
    public JwtAuthenticationResponse signUp(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.signUp(request);
    }

    /*@Operation(summary = "Авторизация пользователя")*/
    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest request) {
        return authenticationService.signIn(request);
    }

    @PostMapping("/auth_logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
       authenticationService.logout(new JwtAuthenticationResponse(token));
       return  ResponseEntity.ok("Logged out successfully!");
    }

}
