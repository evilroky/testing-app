package ru.egor.testingapp.repository;

import ru.egor.testingapp.entity.TestEntity;

import java.util.List;

public interface CrudRepository<T, ID> {
    void create(T entity);
    T read(ID id);
    void update(T entity);
    void delete(ID id);
    List<TestEntity> findAll();
}
