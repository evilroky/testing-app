package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.egor.testingapp.entity.Result;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.ResultRepository;
import ru.egor.testingapp.repository.UserRepository;

import java.util.List;

/**
 * Сервис HistoryService предоставляет функционал для работы с историей прохождения тестов пользователей.
 * Обеспечивает получение результатов прохождения тестов для текущего пользователя.
 */
@Service
public class HistoryService {

    /**
     * Репозиторий для работы с пользователями
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий для работы с результатами тестов
     */
    private final ResultRepository resultRepository;

    /**
     * Конструктор сервиса.
     *
     * @param userRepository   репозиторий пользователей
     * @param resultRepository репозиторий результатов
     */
    @Autowired
    public HistoryService(UserRepository userRepository, ResultRepository resultRepository) {
        this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    /**
     * Возвращает список результатов прохождения тестов для текущего пользователя,
     * отсортированных по дате прохождения в порядке убывания.
     *
     * @return список результатов прохождения тестов текущего пользователя
     */
    public List<Result> getResultsForCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return resultRepository.findAllByPassedBy_IdOrderByPassedDateDesc(user.getId());
    }
}

