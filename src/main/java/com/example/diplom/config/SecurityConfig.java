package com.example.diplom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * Конфигурация безопасности приложения.
 * Определяет правила доступа, аутентификации и шифрования паролей.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Настраивает цепочку фильтров безопасности.
     *
     * @param http Объект конфигурации HTTP-безопасности
     * @return Настроенная цепочка фильтров
     * @throws Exception Ошибка конфигурации
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        // Публичные ресурсы и страницы
                        .requestMatchers("/css/**", "/js/**", "/login", "/", "/logout", "/menu", "/cart/add", "/cart/remove", "/cart/checkout")
                        .permitAll()
                        // Доступ для сотрудников и администраторов
                        .requestMatchers("/cart/all")
                        .hasAnyRole("STAFF", "ADMIN")
                        // Все остальные запросы требуют роли ADMIN
                        .anyRequest()
                        .hasRole("ADMIN")
                )
                // Настройка формы входа
                .formLogin(form -> form
                        .loginPage("/login") // Кастомная страница входа
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler()) // Перенаправление на исходный URL после входа
                        .permitAll()
                )
                // Настройка выхода из системы
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для выхода (POST-запрос)
                        .logoutSuccessUrl("/") // Перенаправление после выхода
                        .invalidateHttpSession(true) // Уничтожение сессии
                        .deleteCookies("JSESSIONID") // Удаление куки
                        .permitAll()
                )
                // Обработка ошибок доступа
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/login?error=access_denied") // Страница при отказе в доступе
                );

        return http.build();
    }

    /**
     * Настройка кодировщика паролей.
     * Используется BCrypt для хеширования паролей.
     *
     * @return Реализация PasswordEncoder с алгоритмом BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}