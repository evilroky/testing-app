package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.User;

import java.util.List;

@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {

    //Находит всех пользователей с заданными именем
    List<User> findByUsername(String username);


}
