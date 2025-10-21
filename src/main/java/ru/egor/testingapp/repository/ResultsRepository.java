package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Results;

public interface ResultsRepository extends CrudRepository<Results, Long> {
}
