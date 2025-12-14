package ru.egor.testingapp.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Класс конфигурации Spring Security, который настраивает правила аутентификации и авторизации для приложения.
 * Настраивает кодирование паролей, цепочку фильтров безопасности и контроль доступа к различным конечным точкам.
 * Определяет поведение входа и выхода, а также обработку отказа в доступе.
 */
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig {

    /**
     * Возвращает кодировщик паролей для приложения.
     *
     * @return кодировщик паролей
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Настраивает цепочку фильтров безопасности для приложения.
     * Устанавливает обработку исключений, правила авторизации, а также конфигурации входа и выхода.
     *
     * @param http объект {@link HttpSecurity} для настройки
     * @return настроенный объект {@link SecurityFilterChain}
     * @throws Exception если происходит ошибка во время конфигурации
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/access-denied");
                        })
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/login", "/registration",
                                "/css/**", "/js/**").permitAll()
                        .requestMatchers("/swagger-ui/**", "/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );
        return http.build();
    }
}
