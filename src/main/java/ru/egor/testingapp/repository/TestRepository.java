package ru.egor.testingapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Tests;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс TestRepository предоставляет методы для работы с сущностью Test в базе данных.
 * Расширяет CrudRepository для базовых операций CRUD и добавляет специализированные методы поиска.
 *
 * @see Tests
 * @see CrudRepository
 */
@RepositoryRestResource(path = "tests")
public interface TestRepository extends CrudRepository<Tests, Long> {

    /**
     * Возвращает список всех уникальных тем тестов, отсортированных по возрастанию.
     *
     * @return список тем тестов
     */
    @Query("SELECT DISTINCT t.theme FROM Tests t ORDER BY t.theme ASC")
    List<String> findAllThemesSorted();

    /**
     * Находит все тесты по указанной теме.
     *
     * @param theme тема теста
     * @return список тестов с указанной темой
     */
    List<Tests> findByTheme(String theme);

    /**
     * Находит все тесты автора, отсортированные по дате создания в порядке убывания.
     *
     * @param authorId идентификатор автора
     * @return список тестов автора, отсортированных по дате создания
     */
    List<Tests> findAllByAuthor_IdOrderByCreateDateDesc(Long authorId);

    /**
     * Находит тест по названию.
     *
     * @param title название теста
     * @return Optional объекта теста с указанным названием
     */
    Optional<Tests> findByTitle(String title);

    /**
     * Возвращает список всех тестов.
     *
     * @return список всех тестов
     */
    List<Tests> findAll();
}

