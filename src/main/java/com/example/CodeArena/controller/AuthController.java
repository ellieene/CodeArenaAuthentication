package com.example.CodeArena.controller;

import com.example.CodeArena.model.dto.UserDTO;
import com.example.CodeArena.model.request.UserAuthorizationRequest;
import com.example.CodeArena.model.request.UserRegistrationRequest;
import com.example.CodeArena.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthController {

    private final UserService userService;

    @Operation(summary = "Registration")
    @PostMapping("/registration")
    public ResponseEntity<String> registration(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest) {
        userService.registration(userRegistrationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Пользователь успешно зарегистрирован");
    }

    @Operation(summary = "Authorization")
    @PostMapping("/authorization")
    public ResponseEntity<String> authorization(@RequestBody UserAuthorizationRequest userAuthorizationResponse) {
        return null;
    }

    @Operation(summary = "Получение текущего пользователя")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> me() {
        return null;
    }

    @Operation(summary = "Обновление JWT токена")
    @PostMapping("/refresh")
    public ResponseEntity<String> refresh() {
        return null;
    }


}
