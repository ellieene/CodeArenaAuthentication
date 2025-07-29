package com.example.CodeArena.service;

import com.example.CodeArena.config.security.component.JwtTokenProvider;
import com.example.CodeArena.exception.DuplicateDataException;
import com.example.CodeArena.exception.EntityNotFoundException;
import com.example.CodeArena.exception.InvalidCredentialsException;
import com.example.CodeArena.model.dto.UserDTO;
import com.example.CodeArena.model.entity.User;
import com.example.CodeArena.model.enums.Role;
import com.example.CodeArena.model.request.UserAuthorizationRequest;
import com.example.CodeArena.model.request.UserRegistrationRequest;
import com.example.CodeArena.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.example.CodeArena.util.CommonStrings.*;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final ModelMapper modelMapper;

    /**
     * Registration
     *
     * @param userRegistrationRequest {@link UserRegistrationRequest}
     */
    @Transactional
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

    /**
     * Authorization
     *
     * @param userAuthorizationRequest {@link UserAuthorizationRequest}
     * @return JWT token
     */
    @Transactional
    public String authorization(UserAuthorizationRequest userAuthorizationRequest) {
        User user = userRepository
                .findByEmailOrUsername(userAuthorizationRequest.username(), userAuthorizationRequest.username())
                .orElseThrow(() -> new EntityNotFoundException(UNCORRECT_EMAIL_AND_LOGIN));
        if(!passwordEncoder.matches(userAuthorizationRequest.password(), user.getPassword())){
            throw new InvalidCredentialsException(UNCORRECT_PASSWORD);
        }
        return jwtTokenProvider.generateToken(user);
    }

    /**
     * Получение пользователя
     *
     */
    public UserDTO me(String login) {
        User user = userRepository
                .findByEmailOrUsername(login, login)
                .orElseThrow(() -> new EntityNotFoundException(UNCORRECT_EMAIL_AND_LOGIN));
        UserDTO userDTO = new UserDTO();
        modelMapper.map(user, userDTO);
        return userDTO;
    }
}
