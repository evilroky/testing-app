package ru.egor.testingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.egor.testingapp.entity.Role;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.RoleRepository;
import ru.egor.testingapp.repository.UserRepository;

import java.time.LocalDateTime;

@Controller
public class WebController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public WebController(PasswordEncoder passwordEncoder, UserRepository userRepository, RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @GetMapping("/registration")
    public String registrationForm() {
        return "registration";
    }

    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        try {
            enum roles{
                ROLE_USER,
                ROLE_ADMIN
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Role role = roleRepository.findRoleByName(roles.ROLE_USER.name());
            user.setRole(role);
            user.setCreateDate(LocalDateTime.now());
            userRepository.save(user);
            return "redirect:/login";
        } catch (Exception e) {
            model.addAttribute("message", "Ошибка регистрации: пользователь уже существует");
            return "registration";
        }
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/logout")
    public String logoutForm() {
        return "logout";
    }

}
