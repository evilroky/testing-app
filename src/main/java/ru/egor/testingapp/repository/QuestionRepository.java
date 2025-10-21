package ru.egor.testingapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Question;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Long> {

    //Выводит все Вопросы по одному конкретному тесту
    @Query("FROM Question q WHERE q.test.title = :title")
    List<Question> findByTestsTitle(String title);
}
