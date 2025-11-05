package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Role;

@RepositoryRestResource(path = "roles")
public interface RoleRepository extends CrudRepository<Role, Long> {
    Role findRoleByName(String name);
}
