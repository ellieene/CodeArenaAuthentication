package com.example.CodeArena.controller;

import com.example.CodeArena.config.annotation.ValidLoginAndEmail;
import com.example.CodeArena.model.dto.UserDTO;
import com.example.CodeArena.model.request.UserAuthorizationRequest;
import com.example.CodeArena.model.request.UserRegistrationRequest;
import com.example.CodeArena.model.request.UsernameRequest;
import com.example.CodeArena.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/auth")
@Validated
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
    public ResponseEntity<String> authorization(@Valid @RequestBody UserAuthorizationRequest userAuthorizationResponse) {
        return ResponseEntity.ok(userService.authorization(userAuthorizationResponse));
    }

    @Operation(summary = "Получение текущего пользователя")
    @GetMapping("/me")
    public ResponseEntity<UserDTO> me(@Valid @ValidLoginAndEmail String username) {
        return ResponseEntity.ok(userService.me(username));
    }
}
