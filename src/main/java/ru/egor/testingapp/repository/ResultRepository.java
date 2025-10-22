package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Result;

public interface ResultRepository extends CrudRepository<Result, Long> {
}
