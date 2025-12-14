package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Answer;
import ru.egor.testingapp.entity.Question;

import java.util.List;

/**
 * Интерфейс AnswerRepository предоставляет методы для работы с сущностью Answer в базе данных.
 * Расширяет CrudRepository для базовых операций CRUD и добавляет специализированные методы поиска.
 *
 * @see Answer
 * @see CrudRepository
 */
@RepositoryRestResource(path = "answers")
public interface AnswerRepository extends CrudRepository<Answer, Long> {

    /**
     * Находит все ответы, связанные с конкретным вопросом по идентификатору вопроса.
     *
     * @param questionId идентификатор вопроса
     * @return список ответов, связанных с указанным вопросом
     */
    List<Answer> findByQuestionId(Long questionId);

    /**
     * Находит все ответы, связанные с конкретным вопросом.
     *
     * @param question объект вопроса
     * @return список ответов, связанных с указанным вопросом
     */
    List<Answer> findByQuestion(Question question);
}

