package com.example.CodeArena.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CommonStrings {

    public static final String UNCORRECT_PASSWORD = "Неверный пароль.";
    public static final String UNCORRECT_EMAIL_AND_LOGIN = "Email или login не верный.";
    public static final String LOGIN_IS_TAKEN = "Логин уже занят.";
    public static final String EMAIL_IS_TAKEN = "Email уже занят.";


    // Константы с сообщениями об ошибках с JWT токеном
    public static final String EXPIRED_JWT_EXCEPTION = "Срок действия токена истек";
    public static final String UNSUPPORTED_JWT_EXCEPTION = "Неподдерживаемый токен";
    public static final String MALFORMED_JWT_EXCEPTION = "Неверный токен";
    public static final String SIGNATURE_EXCEPTION = "Неверная подпись токена";
    public static final String ILLEGAL_ARGUMENT_EXCEPTION = "Неверный токен";
    public static final String JWT_EXCEPTION = "Ошибка с токеном";

}

