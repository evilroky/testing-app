package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Report;

@RepositoryRestResource(path = "reports")
public interface ReportRepository extends CrudRepository<Report, Long> {
}
