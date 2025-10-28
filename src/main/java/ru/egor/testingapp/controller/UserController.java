package ru.egor.testingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/main/users")
public class UserController {

    private final UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/findByUsername/{username}")
    public List<User> findByUsername (@PathVariable String username) {
        return userRepository.findByUsername(username);
    }
}
