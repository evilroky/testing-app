package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {

    //Находит всех пользователей с заданными именем
    List<User> findByUsername(String username);


}
