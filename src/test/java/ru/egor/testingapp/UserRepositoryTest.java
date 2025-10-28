package ru.egor.testingapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.criteria.UsersRepositoryCustom;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.UserRepository;

import java.util.UUID;


@SpringBootTest
@Transactional
class UserRepositoryTest {

    private final UserRepository userRepository;
    private final UsersRepositoryCustom usersRepositoryCustom;

    @Autowired
    UserRepositoryTest(UserRepository userRepository, UsersRepositoryCustom usersRepositoryCustom) {
        this.userRepository = userRepository;
        this.usersRepositoryCustom = usersRepositoryCustom;
    }

    @BeforeEach
    void clearDatabaseBF(){
        userRepository.deleteAll();
    }

    @AfterEach
    void clearDatabaseAF(){
        userRepository.deleteAll();
    }


    @Test
    void testFindUserByUsername() {
        String username = UUID.randomUUID().toString();

        User user = new User();
        user.setUsername(username);
        userRepository.save(user);

        User foundUser = userRepository.findByUsername(username).getFirst();

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(username, foundUser.getUsername());
    }

    @Test
    void testFindUserByUsernameCustom() {
        String username = UUID.randomUUID().toString();

        User user = new User();
        user.setUsername(username);
        usersRepositoryCustom.save(user);

        User foundUser = usersRepositoryCustom.findByUsername(username).getFirst();

        Assertions.assertNotNull(foundUser);
        Assertions.assertEquals(user.getId(), foundUser.getId());
        Assertions.assertEquals(username, foundUser.getUsername());

    }

}
