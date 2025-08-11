package com.example.authentification.config.swagger;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Конфигурационный класс для настройки Swagger/OpenAPI документации.
 * <p>
 * Настраивает:
 * <ul>
 *   <li>Основную информацию о API (название, версия)</li>
 *   <li>Механизм аутентификации через JWT токен</li>
 *   <li>Схему безопасности для всех защищенных endpoints</li>
 * </ul>
 *
 * @see OpenAPI
 * @see SecurityScheme
 */
@Configuration
public class SwaggerConfig {

    /**
     * Создает кастомную конфигурацию OpenAPI.
     * <p>
     * Конфигурация включает:
     * <ul>
     *   <li>Основную информацию о API </li>
     *   <li>Настройку аутентификации через JWT (bearer token)</li>
     *   <li>Определение схемы безопасности "bearerAuth" для использования в аннотациях</li>
     * </ul>
     *
     * @return сконфигурированный объект {@link OpenAPI}
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Code Arena").version("1.0"))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("bearerAuth", new SecurityScheme()
                                .type(Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }
}

