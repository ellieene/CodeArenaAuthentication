package com.example.CodeArena.service;

import com.example.CodeArena.exception.DuplicateDataException;
import com.example.CodeArena.model.entity.User;
import com.example.CodeArena.model.enums.Role;
import com.example.CodeArena.model.request.UserRegistrationRequest;
import com.example.CodeArena.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Registration
     *
     * @param userRegistrationRequest {@link UserRegistrationRequest}
     */
    @Transactional
    public void registration(UserRegistrationRequest userRegistrationRequest) {
        userRepository.findByEmailOrUsername(userRegistrationRequest.email(), userRegistrationRequest.username()).ifPresent(user -> {
            if (user.getUsername().equals(userRegistrationRequest.username())) {
                throw new DuplicateDataException("Login уже занят");
            }
            if (user.getEmail().equals(userRegistrationRequest.email())) {
                throw new DuplicateDataException("Email уже занят");
            }
        });
        User newUser = User.builder()
                .email(userRegistrationRequest.email())
                .password(passwordEncoder.encode(userRegistrationRequest.password()))
                .username(userRegistrationRequest.username())
                .role(Role.USER)
                .build();
        userRepository.save(newUser);
    }
}
