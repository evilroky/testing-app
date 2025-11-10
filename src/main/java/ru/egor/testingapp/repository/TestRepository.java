package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Test;

@RepositoryRestResource(path = "tests")
public interface TestRepository extends CrudRepository<Test, Long> {

    void deleteByTitle(String title);

}
