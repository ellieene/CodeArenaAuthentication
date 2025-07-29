package com.example.CodeArena.config.security.component;

import com.example.CodeArena.model.entity.User;
import com.example.CodeArena.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@RequiredArgsConstructor
@Component
/**
 * Класс для работы с JWT токенами: генерации, валидации и извлечения данных.
 * <p>
 * Использует симметричное шифрование на основе секретного ключа (HMAC-SHA256).
 */
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String base64Secret;
    private SecretKey secret;

    /**
     * Инициализирует секретный ключ на основе base64-строки из конфигурации.
     * Вызывается автоматически после создания бина и инъекции зависимостей.
     *
∫     * @throws IllegalStateException если секретная строка невалидна или не может быть декодирована
     */
    @PostConstruct
    private void initializeSecret() {
        byte[] keyBytes = Decoders.BASE64.decode(base64Secret);
        this.secret = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * Генерирует JWT токен для указанного пользователя.
     * <p>
     * Токен содержит:
     * <ul>
     *   <li>Subject (email пользователя)</li>
     *   <li>Дополнительные claims: email, роль и имя пользователя</li>
     *   <li>Время выдачи (issued at)</li>
     *   <li>Срок действия - 1 час с момента генерации</li>
     * </ul>
     *
     * @param user объект пользователя, для которого генерируется токен
     * @return сгенерированный JWT токен в виде строки
     */
    public String generateToken(User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("email", user.getEmail())
                .claim("role", user.getRole())
                .claim("name", user.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 3600000))  // 1 час
                .signWith(secret, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Извлекает email пользователя из JWT токена.
     *
     * @param token JWT токен для парсинга
     * @return email пользователя, указанный в subject токена
     * @throws JwtException если токен невалиден или не может быть распарсен
     * @throws IllegalArgumentException если токен равен null или пустой
     */
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secret)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Проверяет валидность JWT токена.
     * <p>
     * Проверяет:
     * <ul>
     *   <li>Корректность подписи</li>
     *   <li>Срок действия токена</li>
     *   <li>Общую структуру токена</li>
     * </ul>
     *
     * @param token токен для проверки
     * @return true если токен валиден, false если токен невалиден или истек срок его действия
     */
    public void checkToken(String token) {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
    }
}