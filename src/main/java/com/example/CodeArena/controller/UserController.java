package com.example.CodeArena.controller;

import com.example.CodeArena.config.annotation.ValidLoginAndEmail;
import com.example.CodeArena.model.dto.UserDTO;
import com.example.CodeArena.model.responce.StringResponse;
import com.example.CodeArena.service.user.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "UserController")
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @Operation(summary = "Профиль User")
    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserDTO> getUser(@Valid @PathVariable UUID userId){
        return ResponseEntity.ok(userServiceImpl.getUser(userId));
    }

//    @Operation(summary = "Изменения user")
//    @PutMapping("/profile/edit-user/{userId}")
//    public ResponseEntity<StringResponse> editUser(@Valid @PathVariable UUID userId, @){
//        ResponseEntity.ok(userServiceImpl.editUser(userId));
//
//    }
}
