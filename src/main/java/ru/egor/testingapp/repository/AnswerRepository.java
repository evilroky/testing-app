package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Answer;

public interface AnswerRepository extends CrudRepository<Answer, Integer> {
}
