package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Roles;

public interface RolesRepository extends CrudRepository<Roles, Long> {
}
