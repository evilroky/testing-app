package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Answers;

public interface AnswerRepository extends CrudRepository<Answers, Integer> {
}
