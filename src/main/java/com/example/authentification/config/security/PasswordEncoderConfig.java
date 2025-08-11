package com.example.authentification.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Configuration on the PasswordEncoderConfig
 */
@Configuration
public class PasswordEncoderConfig {


    /**
     * Создает и возвращает кодировщик паролей для хеширования.
     * <p>
     * Используется алгоритм BCrypt с силой хеширования по умолчанию (10).
     *
     * @return реализацию {@link PasswordEncoder} на основе BCrypt
     * @see BCryptPasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
