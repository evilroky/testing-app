package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Answer;

@RepositoryRestResource(path = "answers")
public interface AnswerRepository extends CrudRepository<Answer, Integer> {
}
