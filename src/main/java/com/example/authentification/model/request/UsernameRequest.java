package com.example.authentification.model.request;

import com.example.authentification.config.annotation.ValidLoginAndEmail;

public record UsernameRequest(

    @ValidLoginAndEmail
    String username
){
}
