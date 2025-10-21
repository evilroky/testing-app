package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Tests;

public interface TestsRepository extends CrudRepository<Tests, Long> {

    void deleteByTitle(String title);
}
