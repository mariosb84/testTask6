package com.example.userservice.service;

import com.example.userservice.domain.dto.JwtAuthenticationResponseDto;
import com.example.userservice.domain.dto.model.*;
import com.example.userservice.domain.dto.SignInRequest;
import com.example.userservice.domain.dto.SignUpRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.userservice.filter.JwtAuthenticationFilter.BEARER_PREFIX;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserServiceData userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenBlackListServiceData tokenBlackListServiceData;
    private final UserContactsService userContactsService;
    private final UserPhotoService userPhotoService;

    /**
     * Регистрация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponseDto signUp(SignUpRequest request) {

        var user = User.builder()
                .userLastName(request.getUserLastName())
                .userName(request.getUserName())
                .userMiddleName(request.getUserMiddleName())
                .userBirthDate(request.getUserBirthDate())
                .password(passwordEncoder.encode(request.getPassword()))
                .userContacts(userContactsService.save(new UserContacts()))
                .userPhoto(userPhotoService.save(new UserPhoto()))
                .roles(List.of(Role.ROLE_USER))
                .build();
        userService.add(userService.getUserAdditionDtoFromUser(user));

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDto(jwt);
    }

    /**
     * Аутентификация пользователя
     *
     * @param request данные пользователя
     * @return токен
     */
    public JwtAuthenticationResponseDto signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUserName(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUserName());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponseDto(jwt);
    }

    public void logout(JwtAuthenticationResponseDto jwtAuthenticationResponseDto) {
        var jwt = jwtAuthenticationResponseDto.getToken().substring(BEARER_PREFIX.length());
        JwtAuthenticationResponse jwtAuthenticationResponse = new JwtAuthenticationResponse();
        jwtAuthenticationResponse.setToken(jwt);
        tokenBlackListServiceData.add(jwtAuthenticationResponse);
    }

}
