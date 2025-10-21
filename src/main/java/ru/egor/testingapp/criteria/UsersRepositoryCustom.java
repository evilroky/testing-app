package ru.egor.testingapp.criteria;

import ru.egor.testingapp.entity.Users;

import java.util.List;

public interface UsersRepositoryCustom {

    //Нахождение пользователя по имени
    List<Users> findByUsername(String username);

    void save(Users users);
}
