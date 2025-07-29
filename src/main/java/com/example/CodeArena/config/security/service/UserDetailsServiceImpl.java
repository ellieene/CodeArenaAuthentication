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

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        return userRepository.findByEmailOrUsername(usernameOrEmail, usernameOrEmail)
                .orElseThrow(() ->
                        new EntityNotFoundException(UNCORRECT_EMAIL_AND_LOGIN)
                );
    }
}
