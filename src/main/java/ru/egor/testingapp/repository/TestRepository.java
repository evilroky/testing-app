/*
package ru.egor.testingapp.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.egor.testingapp.entity.TestEntity;

import java.util.List;
import java.util.Optional;

@Component
public class TestRepository implements CrudRepository<TestEntity, Long> {

    private final List<TestEntity> container;

    @Autowired
    public TestRepository(List<TestEntity> container) {
        this.container = container;
    }

    @Override
    public void create(TestEntity entity) {
        container.add(entity);
    }

    @Override
    public TestEntity read(Long id) {
        Optional<TestEntity> found = container.stream().filter(e -> e.getId().equals(id)).findFirst();
        return found.orElse(null);
    }

    @Override
    public void update(TestEntity entity) {
        for (int i = 0; i < container.size(); i++) {
            if (container.get(i).getId().equals(entity.getId())) {
                container.set(i, entity);
                return;
            }
        }
    }

    @Override
    public void delete(Long id) {
        container.removeIf(e -> e.getId().equals(id));
    }

    @Override
    public List<TestEntity> findAll() {
        return List.copyOf(container);
    }
}
*/
