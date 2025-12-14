package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.entity.Tests;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.TestRepository;
import ru.egor.testingapp.repository.UserRepository;

import java.util.List;

/**
 * Сервис ProfileService предоставляет функционал для работы с профилем пользователя и его тестами.
 * Обеспечивает управление данными пользователя, его тестами и обновление профиля.
 */
@Service
public class ProfileService {

    /**
     * Репозиторий для работы с тестами
     */
    private final TestRepository testRepository;

    /**
     * Репозиторий для работы с пользователями
     */
    private final UserRepository userRepository;

    /**
     * Кодировщик паролей
     */
    private final PasswordEncoder passwordEncoder;

    /**
     * Конструктор сервиса.
     *
     * @param testRepository  репозиторий тестов
     * @param userRepository  репозиторий пользователей
     * @param passwordEncoder кодировщик паролей
     */
    @Autowired
    public ProfileService(TestRepository testRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.testRepository = testRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Возвращает текущего пользователя.
     *
     * @return объект текущего пользователя
     */
    public User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));
    }

    /**
     * Возвращает список тестов текущего пользователя, отсортированных по дате создания в порядке убывания.
     *
     * @return список тестов текущего пользователя
     */
    public List<Tests> getTestsOfCurrentUser() {
        User user = getCurrentUser();
        return testRepository.findAllByAuthor_IdOrderByCreateDateDesc(user.getId());
    }

    /**
     * Обновляет имя пользователя.
     *
     * @param newName новое имя пользователя
     */
    @Transactional
    public void updateUsername(String newName) {
        User user = getCurrentUser();
        user.setUsername(newName);
        userRepository.save(user);
    }

    /**
     * Обновляет пароль пользователя.
     *
     * @param newPassword новый пароль пользователя
     */
    @Transactional
    public void updatePassword(String newPassword) {
        User user = getCurrentUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    /**
     * Удаляет тест по его идентификатору.
     *
     * @param id идентификатор теста
     */
    @Transactional
    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }
}

