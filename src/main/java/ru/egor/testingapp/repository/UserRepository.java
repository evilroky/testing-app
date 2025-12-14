package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.User;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс UserRepository предоставляет методы для работы с сущностью User в базе данных.
 * Расширяет CrudRepository для базовых операций CRUD и добавляет специализированные методы поиска.
 *
 * @see User
 * @see CrudRepository
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Находит пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return Optional объекта пользователя с указанным именем
     */
    Optional<User> findByUsername(String username);

    /**
     * Возвращает список всех пользователей.
     *
     * @return список всех пользователей
     */
    List<User> findAll();

    /**
     * Находит пользователя по идентификатору.
     *
     * @param id идентификатор пользователя
     * @return пользователь с указанным идентификатором
     */
    User findById(long id);
}

