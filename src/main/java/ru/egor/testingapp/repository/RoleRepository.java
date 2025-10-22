package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
}
