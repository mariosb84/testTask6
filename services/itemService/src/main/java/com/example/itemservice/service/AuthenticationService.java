package com.example.itemservice.service;

import com.example.itemservice.domain.dto.JwtAuthenticationResponseDto;
import com.example.itemservice.domain.model.JwtAuthenticationResponse;
import com.example.itemservice.domain.dto.SignInRequest;
import com.example.itemservice.domain.dto.SignUpRequest;
import com.example.itemservice.domain.model.Role;
import com.example.itemservice.domain.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.itemservice.filter.JwtAuthenticationFilter.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserServiceData userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenBlackListServiceData tokenBlackListServiceData;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
   /* public JwtAuthenticationResponse signUp(SignUpRequest request) {*/
    public JwtAuthenticationResponseDto signUp(SignUpRequest request) {

        var user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phone(request.getPhone())
                .roles(List.of(Role.ROLE_USER))
                .build();

       /* var user = new User(request.getUsername(), request.getEmail(), );*/

        userService.add(user);

        var jwt = jwtService.generateToken(user);
        /*return new JwtAuthenticationResponse(jwt);*/
        return new JwtAuthenticationResponseDto(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    /*public JwtAuthenticationResponse signIn(SignInRequest request) {*/
    public JwtAuthenticationResponseDto signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        /*return new JwtAuthenticationResponse(jwt);*/
        return new JwtAuthenticationResponseDto(jwt);
    }

    /*public void logout(JwtAuthenticationResponse jwtAuthenticationResponse) {*/
    public void logout(JwtAuthenticationResponseDto jwtAuthenticationResponseDto) {
        /*var jwt = jwtAuthenticationResponse.getToken().substring(BEARER_PREFIX.length());*/
        var jwt = jwtAuthenticationResponseDto.getToken().substring(BEARER_PREFIX.length());
        /*jwtAuthenticationResponse.setToken(jwt);*/
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        tokenBlackListServiceData.add(jwtAuthenticationResponse);
    }

}
