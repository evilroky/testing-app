package ru.egor.testingapp.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.egor.testingapp.entity.Role;

import java.util.List;

/**
 * Интерфейс RoleRepository предоставляет методы для работы с сущностью Role в базе данных.
 * Расширяет CrudRepository для базовых операций CRUD и добавляет специализированные методы поиска.
 *
 * @see Role
 * @see CrudRepository
 */
@RepositoryRestResource(path = "roles")
public interface RoleRepository extends CrudRepository<Role, Long> {

    /**
     * Находит роль по ее названию.
     *
     * @param name название роли
     * @return объект роли с указанным названием
     */
    Role findRoleByName(String name);

    /**
     * Возвращает список всех ролей.
     *
     * @return список всех ролей
     */
    List<Role> findAll();
}

