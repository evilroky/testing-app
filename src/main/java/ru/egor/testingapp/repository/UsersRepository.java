package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import ru.egor.testingapp.entity.Users;

import java.util.List;

public interface UsersRepository extends CrudRepository<Users, Long> {

    //Находит всех пользователей с заданными именем
    List<Users> findByUsername(String username);


}
