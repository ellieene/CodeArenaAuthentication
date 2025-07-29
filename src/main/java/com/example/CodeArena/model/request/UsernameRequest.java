package com.example.CodeArena.model.request;

import com.example.CodeArena.config.annotation.ValidLoginAndEmail;

public record UsernameRequest(

    @ValidLoginAndEmail
    String username
){
}
