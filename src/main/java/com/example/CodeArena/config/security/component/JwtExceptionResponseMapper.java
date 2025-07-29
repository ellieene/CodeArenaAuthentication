package com.example.CodeArena.config.security.component;

import com.example.CodeArena.model.responce.StringResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.example.CodeArena.util.CommonStrings.*;

/**
 * Компонент для маппинга исключений JWT в JSON-ответы с корректным HTTP-статусом и сообщением.
 * Используется в фильтрах безопасности для обработки ошибок токена до входа в контроллер.
 */
@Component
@RequiredArgsConstructor
public class JwtExceptionResponseMapper {

    private final ObjectMapper objectMapper;

    /**
     * Обрабатывает исключение, связанное с JWT, и отправляет корректный JSON-ответ в клиент.
     *
     * @param response HTTP-ответ
     * @param e        исключение, выброшенное в процессе обработки JWT
     * @throws IOException если произошла ошибка записи в ответ
     */
    public void handleJwtException(HttpServletResponse response, Exception e) throws IOException {
        int status = mapToStatusCode(e);
        String message = mapToMessage(e);

        response.setStatus(status);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        String json = objectMapper.writeValueAsString(new StringResponse(message));
        response.getWriter().write(json);
    }

    /**
     * Возвращает сообщение об ошибке на основе типа исключения.
     *
     * @param e исключение JWT
     * @return строка с сообщением
     */
    private String mapToMessage(Exception e) {
        if (e instanceof ExpiredJwtException) return EXPIRED_JWT_EXCEPTION;
        if (e instanceof UnsupportedJwtException) return UNSUPPORTED_JWT_EXCEPTION;
        if (e instanceof MalformedJwtException) return MALFORMED_JWT_EXCEPTION;
        if (e instanceof SignatureException) return SIGNATURE_EXCEPTION;
        if (e instanceof IllegalArgumentException) return ILLEGAL_ARGUMENT_EXCEPTION;
        return JWT_EXCEPTION;
    }

    /**
     * Возвращает соответствующий HTTP-статус на основе типа исключения.
     *
     * @param e исключение JWT
     * @return HTTP статус
     */
    private int mapToStatusCode(Exception e) {
        if (e instanceof IllegalArgumentException) {
            return HttpStatus.BAD_REQUEST.value();
        }
        return HttpStatus.UNAUTHORIZED.value();
    }
}