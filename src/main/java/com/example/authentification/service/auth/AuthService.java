package com.example.authentification.service.auth;

import com.example.authentification.exception.DuplicateDataException;
import com.example.authentification.exception.EntityNotFoundException;
import com.example.authentification.exception.InvalidCredentialsException;
import com.example.authentification.model.request.UserAuthorizationRequest;
import com.example.authentification.model.request.UserRegistrationRequest;

/**
 * Сервис для обработки операций аутентификации и регистрации пользователей.
 * <p>
 * Предоставляет методы для:
 * <ul>
 *   <li>Регистрации новых пользователей в системе</li>
 *   <li>Аутентификации существующих пользователей</li>
 * </ul>
 *
 * @see UserRegistrationRequest
 * @see UserAuthorizationRequest
 */
public interface AuthService {

    /**
     * Регистрирует нового пользователя в системе.
     * <p>
     * При регистрации:
     * <ul>
     *   <li>Проверяет уникальность email/login</li>
     *   <li>Хеширует пароль перед сохранением</li>
     *   <li>Создает запись пользователя в базе данных</li>
     * </ul>
     *
     * @param userRegistrationRequest объект запроса на регистрацию, содержащий данные пользователя
     * @throws DuplicateDataException если пользователь с таким email или логином уже существует
     */
    void registration(UserRegistrationRequest userRegistrationRequest);

    /**
     * Аутентифицирует пользователя и генерирует JWT токен.
     * <p>
     * Процесс аутентификации включает:
     * <ul>
     *   <li>Проверку учетных данных пользователя</li>
     *   <li>Сравнение хеша пароля</li>
     *   <li>Генерацию JWT токена при успешной аутентификации</li>
     * </ul>
     *
     * @param userAuthorizationRequest объект запроса на авторизацию, содержащий учетные данные
     * @return JWT токен для аутентифицированного пользователя
     * @throws InvalidCredentialsException если аутентификация не удалась из-за пароля
     * @throws EntityNotFoundException если пользователь с указанными данными не найден
     */
    String authorization(UserAuthorizationRequest userAuthorizationRequest);
}
