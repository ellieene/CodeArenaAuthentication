package com.example.CodeArena.service.user;

import com.example.CodeArena.model.dto.UserDTO;

import java.util.UUID;


/**
 * Service to the user
 */
public interface UserService {

    /**
     * Получение пользователя
     *
     */
    UserDTO getUser(UUID userId);

    /**
     * Изменение пользователя
     */
//    String editUser(UUID userId);
}
