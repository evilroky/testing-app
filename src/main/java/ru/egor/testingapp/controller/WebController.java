package ru.egor.testingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.egor.testingapp.entity.Role;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.RoleRepository;
import ru.egor.testingapp.repository.UserRepository;

import java.time.LocalDateTime;

/**
 * Веб-контроллер для обработки пользовательских запросов, таких как главная страница,
 * вход, регистрация и доступ к запрещённым страницам.
 * Отвечает за регистрацию пользователей, аутентификацию и назначение ролей.
 */
@Controller
@RequestMapping("")
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

    /**
     * Обрабатывает GET-запросы на корневой URL ("/").
     * Возвращает имя представления для отображения главной страницы сайта.
     *
     * @return имя представления "index"
     */
    @GetMapping("/")
    public String getHome() {
        return "index";
    }

    /**
     * Обрабатывает GET-запросы на URL "/registration".
     * Возвращает имя представления для отображения формы регистрации пользователя.
     *
     * @return имя представления "registration"
     */
    @GetMapping("/registration")
    public String getRegistrationForm() {
        return "registration";
    }

    /**
     * Обрабатывает POST-запросы на URL "/registration".
     * Регистрирует нового пользователя, кодирует пароль, назначает роль и сохраняет данные в базе.
     * При успешной регистрации перенаправляет на страницу входа.
     * При ошибке регистрации (например, дубликат пользователя) возвращает пользователя на форму регистрации с сообщением об ошибке.
     *
     * @param user  объект пользователя с данными для регистрации
     * @param model модель для добавления атрибутов при ошибке
     * @return имя представления для перенаправления (redirect:/login или registration)
     */
    @PostMapping("/registration")
    public String registerUser(User user, Model model) {
        try {
            enum roles {
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
            model.addAttribute("error", "Ошибка регистрации: пользователь уже существует");
            return "registration";
        }
    }

    /**
     * Обрабатывает GET-запросы на URL "/login".
     * Возвращает имя представления для отображения формы входа пользователя.
     *
     * @return имя представления "login"
     */
    @GetMapping("/login")
    public String getLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {

        if (error != null) {
            model.addAttribute("error", "Неверный логин или пароль");
        }
        return "login";

    }

    /**
     * Обрабатывает GET-запросы на URL "/access-denied".
     * Обрабатывает случаи запрещённого доступа, добавляя flash-атрибуты с сообщением об ошибке
     * и перенаправляет пользователя на главную страницу.
     *
     * @param ra атрибуты для перенаправления
     * @return строка перенаправления на главную страницу
     */
    @GetMapping("/access-denied")
    public String accessDenied(RedirectAttributes ra) {
        ra.addFlashAttribute("messageType", "error");
        ra.addFlashAttribute("message", "Доступ запрещён!");
        return "redirect:/";
    }
}
