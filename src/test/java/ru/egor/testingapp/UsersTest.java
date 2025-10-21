package ru.egor.testingapp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.criteria.UsersRepositoryCustom;
import ru.egor.testingapp.entity.Users;
import ru.egor.testingapp.repository.UsersRepository;

import java.util.UUID;


@SpringBootTest
class UsersTest {

    private final UsersRepository usersRepository;
    private final UsersRepositoryCustom usersRepositoryCustom;

    @Autowired
    UsersTest(UsersRepository usersRepository, UsersRepositoryCustom usersRepositoryCustom) {
        this.usersRepository = usersRepository;
        this.usersRepositoryCustom = usersRepositoryCustom;
    }


    @Test
    void testFindUserByUsername() {
        String username = UUID.randomUUID().toString();

        Users user = new Users();
        user.setUsername(username);
        usersRepository.save(user);

        Users foundUser = usersRepository.findByUsername(username).getFirst();

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(username, foundUser.getUsername());
    }

    @Test
    void testFindUserByUsernameCustom() {
        String username = UUID.randomUUID().toString();

        Users user = new Users();
        user.setUsername(username);
        usersRepositoryCustom.save(user);

        Users foundUser = usersRepositoryCustom.findByUsername(username).getFirst();

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(username, foundUser.getUsername());

    }

}
