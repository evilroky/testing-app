package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Test;

public interface TestRepository extends CrudRepository<Test, Long> {

    void deleteByTitle(String title);
}
