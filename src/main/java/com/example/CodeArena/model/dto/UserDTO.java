package com.example.CodeArena.model.dto;

import com.example.CodeArena.model.enums.Role;
import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String email;
    private Role role;
    private int points;
}
