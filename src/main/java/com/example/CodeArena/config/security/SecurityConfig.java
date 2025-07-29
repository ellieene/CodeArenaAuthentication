package com.example.CodeArena.config.security;

import com.example.CodeArena.config.security.component.JwtTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


/**
 * Configuration on the Secyrity
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenFilter jwtTokenFilter;

    /**
     * Настраивает цепочку фильтров безопасности для приложения.
     * <p>
     * Конфигурация включает:
     * <ul>
     *   <li>Отключение CSRF защиты (так как используется JWT)</li>
     *   <li>Отключение форм-логина и базовой HTTP аутентификации</li>
     *   <li>Установку политики без сохранения состояния (STATELESS)</li>
     *   <li>Настройку правил авторизации для различных эндпоинтов</li>
     *   <li>Добавление кастомного JWT фильтра перед стандартным фильтром аутентификации</li>
     * </ul>
     *
     * @param http объект {@link HttpSecurity} для настройки безопасности
     * @return сконфигурированную цепочку фильтров {@link SecurityFilterChain}
     * @throws Exception если произошла ошибка в процессе конфигурации
     * @see JwtTokenFilter
     * @see UsernamePasswordAuthenticationFilter
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable) // ВАЖНО
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/auth/registration",
                                "auth/authorization",
                                "user/email",
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/swagger-resources/**",
                                "/webjars/**").permitAll()
                        .requestMatchers("/user/me").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated()
                )
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

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
