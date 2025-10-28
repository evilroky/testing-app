package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Result;

@RepositoryRestResource(path = "results")
public interface ResultRepository extends CrudRepository<Result, Long> {
}
