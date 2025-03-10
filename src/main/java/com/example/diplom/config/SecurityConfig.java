package com.example.diplom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/css/**", "/js/**", "/login","/", "/logout","/menu", "/cart/add", "/cart/remove", "/cart/checkout")
                        .permitAll()
                        .requestMatchers("/cart/all")
                        .hasAnyRole("STAFF", "ADMIN")
                        .anyRequest()
                        .hasRole("ADMIN")


                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(new SavedRequestAwareAuthenticationSuccessHandler())
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout") // URL для выхода (POST-запрос)
                        .logoutSuccessUrl("/") // Куда перенаправить после logout
                        .invalidateHttpSession(true) // Уничтожить сессию
                        .deleteCookies("JSESSIONID") // Удалить куки
                        .permitAll()
                )
                .exceptionHandling(handling -> handling
                        .accessDeniedPage("/login?error=access_denied") // Редирект на логин с параметром ошибки
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}