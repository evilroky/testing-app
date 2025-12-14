package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Result;

import java.util.List;

/**
 * Интерфейс ResultRepository предоставляет методы для работы с сущностью Result в базе данных.
 * Расширяет CrudRepository для базовых операций CRUD и добавляет специализированные методы поиска.
 *
 * @see Result
 * @see CrudRepository
 */
@RepositoryRestResource(path = "results")
public interface ResultRepository extends CrudRepository<Result, Long> {

    /**
     * Находит все результаты, связанные с конкретным пользователем по идентификатору пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список результатов, связанных с указанным пользователем
     */
    List<Result> findByPassedById(Long userId);

    /**
     * Находит последний результат по пользователю и тесту, отсортированный по дате прохождения в порядке убывания.
     *
     * @param userId идентификатор пользователя
     * @param testId идентификатор теста
     * @return последний результат прохождения теста пользователем
     */
    Result findFirstByPassedBy_IdAndTests_IdOrderByPassedDateDesc(Long userId, Long testId);

    /**
     * Находит все результаты по пользователю, отсортированные по дате прохождения в порядке убывания.
     *
     * @param userId идентификатор пользователя
     * @return список результатов прохождения тестов пользователем, отсортированных по дате
     */
    List<Result> findAllByPassedBy_IdOrderByPassedDateDesc(Long userId);
}

