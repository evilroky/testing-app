package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.UserRepository;

/**
 * Сервис CustomUserDetailsService реализует интерфейс UserDetailsService для аутентификации пользователей.
 * Предоставляет функционал загрузки пользовательских данных по имени пользователя.
 *
 * @see UserDetailsService
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * Репозиторий для работы с пользователями
     */
    private final UserRepository userRepository;

    /**
     * Конструктор сервиса.
     *
     * @param userRepository репозиторий пользователей
     */
    @Autowired
    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Загружает пользовательские данные по имени пользователя.
     *
     * @param username имя пользователя
     * @return объект UserDetails с информацией о пользователе
     * @throws UsernameNotFoundException если пользователь не найден
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = new User();
            if (userRepository.findByUsername(username).isPresent()) {
                user = userRepository.findByUsername(username).get();
            } else throw new NullPointerException();

            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .authorities(user.getRole().getName())
                    .build();
        } catch (Exception e) {
            throw new UsernameNotFoundException(e.getMessage());
        }
    }
}

