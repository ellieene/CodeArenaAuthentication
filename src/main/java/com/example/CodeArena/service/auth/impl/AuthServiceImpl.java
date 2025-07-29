package com.example.CodeArena.service.auth.impl;

import com.example.CodeArena.config.security.component.JwtTokenProvider;
import com.example.CodeArena.exception.DuplicateDataException;
import com.example.CodeArena.exception.EntityNotFoundException;
import com.example.CodeArena.exception.InvalidCredentialsException;
import com.example.CodeArena.model.entity.User;
import com.example.CodeArena.model.enums.Role;
import com.example.CodeArena.model.request.UserAuthorizationRequest;
import com.example.CodeArena.model.request.UserRegistrationRequest;
import com.example.CodeArena.repository.UserRepository;
import com.example.CodeArena.service.auth.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.CodeArena.util.CommonStrings.*;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;


    @Transactional
    @Override
    public void registration(UserRegistrationRequest userRegistrationRequest) {
        userRepository.findByEmailOrUsername(userRegistrationRequest.email(), userRegistrationRequest.username()).ifPresent(user -> {
            if (user.getUsername().equals(userRegistrationRequest.username())) {
                throw new DuplicateDataException(LOGIN_IS_TAKEN);
            }
            if (user.getEmail().equals(userRegistrationRequest.email())) {
                throw new DuplicateDataException(EMAIL_IS_TAKEN);
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

    @Transactional(readOnly = true)
    @Override
    public String authorization(UserAuthorizationRequest userAuthorizationRequest) {
        User user = userRepository
                .findByEmailOrUsername(userAuthorizationRequest.username(), userAuthorizationRequest.username())
                .orElseThrow(() -> new EntityNotFoundException(UNCORRECT_EMAIL_AND_LOGIN));
        if (!passwordEncoder.matches(userAuthorizationRequest.password(), user.getPassword())) {
            throw new InvalidCredentialsException(UNCORRECT_PASSWORD);
        }
        return jwtTokenProvider.generateToken(user);
    }
}
