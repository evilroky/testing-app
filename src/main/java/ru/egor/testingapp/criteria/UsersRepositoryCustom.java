package ru.egor.testingapp.criteria;

import ru.egor.testingapp.entity.User;

import java.util.List;

public interface UsersRepositoryCustom {

    //Нахождение пользователя по имени
    List<User> findByUsername(String username);

    void save(User user);
}
