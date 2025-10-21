package ru.egor.testingapp.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Questions;

import java.util.List;

public interface QuestionsRepository extends CrudRepository<Questions, Long> {

    //Выводит все Вопросы по одному конкретному тесту
    @Query("FROM Questions q WHERE q.test_id.title = :title")
    List<Questions> findByTestsTitle(String title);
}
