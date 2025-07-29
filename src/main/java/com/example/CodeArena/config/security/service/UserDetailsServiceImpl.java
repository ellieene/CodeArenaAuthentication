package com.example.CodeArena.config.security.service;

import com.example.CodeArena.exception.EntityNotFoundException;
import com.example.CodeArena.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.CodeArena.util.CommonStrings.UNCORRECT_EMAIL_AND_LOGIN;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    /**
     * Загружает детали пользователя по его логину или email.
     *
     * @param userLoginOrEmail логин или email пользователя, по которому осуществляется поиск
     * @return объект {@link UserDetails}, содержащий информацию о найденном пользователе
     * @throws UsernameNotFoundException если пользователь с указанным логином/email не найден
     * @throws EntityNotFoundException если пользователь не существует
     *
     * @see UserRepository#findByEmailOrUsername(String, String) (String, String)
     * @see UserDetails
     */
    @Override
    public UserDetails loadUserByUsername(String userLoginOrEmail) throws UsernameNotFoundException {
        return userRepository.findByEmailOrUsername(userLoginOrEmail, userLoginOrEmail)
                .orElseThrow(() ->
                        new EntityNotFoundException(UNCORRECT_EMAIL_AND_LOGIN)
                );
    }
}
