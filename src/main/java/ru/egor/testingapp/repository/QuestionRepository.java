package ru.egor.testingapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Question;

import java.util.List;

/**
 * Интерфейс QuestionRepository предоставляет методы для работы с сущностью Question в базе данных.
 * Расширяет CrudRepository для базовых операций CRUD и добавляет специализированные методы поиска.
 *
 * @see Question
 * @see CrudRepository
 */
@RepositoryRestResource(path = "questions")
public interface QuestionRepository extends CrudRepository<Question, Long> {

    /**
     * Находит все вопросы, связанные с конкретным тестом по названию теста.
     *
     * @param title название теста
     * @return список вопросов, связанных с указанным тестом
     */
    @Query("FROM Question q WHERE q.tests.title = :title")
    List<Question> findByTestsTitle(String title);

    /**
     * Подсчитывает количество вопросов для конкретного теста по идентификатору теста.
     *
     * @param testId идентификатор теста
     * @return количество вопросов для указанного теста
     */
    int countByTestsId(Long testId);

    /**
     * Находит все вопросы для конкретного теста по идентификатору теста.
     *
     * @param testId идентификатор теста
     * @return список вопросов для указанного теста
     */
    List<Question> findByTestsId(Long testId);
}

