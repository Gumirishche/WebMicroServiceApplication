package com.microservice.controllers;

import com.microservice.dto.Token;
import com.microservice.dto.UserRequest;
import com.microservice.services.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager; // Инъекция AuthenticationManager

    @PostMapping("/login")
    public Token login(@RequestBody UserRequest userLoginDto) {
        // Создание аутентификационного токена с логином и паролем
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(userLoginDto.getLogin(), userLoginDto.getPassword());

        // Выполнение аутентификации
        Authentication authentication = authenticationManager.authenticate(authToken);

        if (authentication.isAuthenticated()) {
            // Если аутентификация успешна, возвращаем токен
            return authService.authenticate(userLoginDto.getLogin(), userLoginDto.getPassword());
        }
        throw new RuntimeException("Authentication failed"); // Или выбросьте соответствующее исключение
    }

    @PostMapping("/register")
    public String register(@RequestBody UserRequest userRegistrationDto) {
        authService.register(userRegistrationDto.getLogin(), userRegistrationDto.getPassword());
        return "User registered successfully";
    }
}