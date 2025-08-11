package com.example.authentification.model.dto;

import com.example.authentification.model.enums.Role;
import lombok.Data;

@Data
public class UserDTO {

    private String username;
    private String email;
    private Role role;
    private int points;
}
