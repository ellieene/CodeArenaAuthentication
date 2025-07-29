package com.example.CodeArena.config.security.component;

import com.example.CodeArena.config.security.service.UserDetailsServiceImpl;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final static String HEADER_NAME = "Authorization";
    private final static String HEADER_PREFIX = "Bearer ";
    private final UserDetailsServiceImpl customUserDetailsService;
    private final JwtTokenProvider jwtService;
    private final JwtExceptionResponseMapper exceptionResponseMapper;

    /**
     * Метод для парсинга токена авторизации при запросе
     *
     * @param request     Запрос с данными фильтрации
     * @param response    ответ после фильтрации
     * @param filterChain цепочка фильтров
     */
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader(HEADER_NAME);

            String jwt = null;
            String username = null;

            if (authorizationHeader != null && authorizationHeader.startsWith(HEADER_PREFIX)) {
                jwt = authorizationHeader.substring(7);
                username = jwtService.extractEmail(jwt);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

                jwtService.checkToken(jwt);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }

            filterChain.doFilter(request, response);
        } catch (JwtException | IllegalArgumentException e) {
            exceptionResponseMapper.handleJwtException(response, e);
        }
    }
}