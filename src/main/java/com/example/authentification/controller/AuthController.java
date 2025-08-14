package com.example.authentification.controller;

import com.example.authentification.model.request.UserAuthorizationRequest;
import com.example.authentification.model.request.UserRegistrationRequest;
import com.example.authentification.service.auth.impl.AuthServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final AuthServiceImpl authServiceImpl;

    @Operation(summary = "Registration")
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        authServiceImpl.registration(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован");
    }

    @Operation(summary = "Authorization")
    @PostMapping("/authorization")
    public ResponseEntity<String> authorization(@Valid @RequestBody UserAuthorizationRequest userAuthorizationResponse) {
        return ResponseEntity.ok(authServiceImpl.authorization(userAuthorizationResponse));
    }
}
