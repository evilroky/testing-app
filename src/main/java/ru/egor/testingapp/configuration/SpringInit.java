package ru.egor.testingapp.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.egor.testingapp.entity.*;
import ru.egor.testingapp.repository.*;

import java.time.LocalDateTime;

/**
 * Инициализирует приложение стандартными ролями, пользователями, тестами, вопросами и ответами.
 * Этот класс реализует {@link CommandLineRunner} для выполнения логики инициализации после загрузки контекста приложения.
 * Обеспечивает создание стандартных ролей (ROLE_USER и ROLE_ADMIN), если они не существуют.
 * Кроме того, создает стандартного пользователя и администратора с зашифрованными паролями,
 * а также заполняет базу данных примером теста по языку Java, если он еще не существует.
 */

@Component
public class SpringInit implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SpringInit(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, QuestionRepository questionRepository, TestRepository testRepository, AnswerRepository answerRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
        this.answerRepository = answerRepository;
    }

    /**
     * Инициализирует приложение необходимыми ролями, пользователями и тестовыми данными, если они еще не существуют.
     * Этот метод выполняется при запуске приложения.
     * <p>
     * Убедиться, что роли "ROLE_USER" и "ROLE_ADMIN" присутствуют в базе данных.
     * Если пользователь по умолчанию с логином "user" не существует, создает его с паролем по умолчанию и назначает роль "ROLE_USER".
     * Также создает пример теста с вопросами и ответами по Java.
     * Если пользователь-администратор по умолчанию с логином "admin" не существует, создает его с паролем по умолчанию и назначает роль "ROLE_ADMIN".
     *
     * @param args аргументы командной строки, переданные в приложение
     * @throws Exception если происходит ошибка во время инициализации
     */
    @Override
    public void run(String... args) throws Exception {

        Role roleUser = roleRepository.findRoleByName("ROLE_USER");
        if (roleUser == null) {
            roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);
        }

        Role roleAdmin = roleRepository.findRoleByName("ROLE_ADMIN");
        if (roleAdmin == null) {
            roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
        }

        if (userRepository.findByUsername("user").isEmpty() && testRepository.findByTitle("Тест по языку Java").isEmpty()) {
            User user = new User();
            user.setUsername("user");
            user.setPassword(passwordEncoder.encode("123"));
            user.setRole(roleUser);
            user.setCreateDate(LocalDateTime.now());
            userRepository.save(user);
            Tests tests = new Tests();
            tests.setTitle("Тест по языку Java");
            tests.setTheme("Java");
            tests.setDescription("Данный тест позволяет проверить свои знания по основам языка Java");
            tests.setCreateDate(LocalDateTime.now());
            tests.setAuthor(user);
            testRepository.save(tests);
            Question q = new Question();
            q.setText("Каким языком является Java?");
            q.setTest(tests);
            questionRepository.save(q);
            Question q2 = new Question();
            q2.setText("Как обозначить строку в Java?");
            q2.setTest(tests);
            questionRepository.save(q2);
            Answer a1 = new Answer();
            a1.setText("Разговорным");
            a1.setQuestion(q);
            a1.setCorrect(false);
            answerRepository.save(a1);
            Answer a2 = new Answer();
            a2.setText("Строго-Типизированным");
            a2.setQuestion(q);
            a2.setCorrect(true);
            answerRepository.save(a2);
            Answer a3 = new Answer();
            a3.setText("Адаптивным");
            a3.setQuestion(q);
            a3.setCorrect(false);
            answerRepository.save(a3);
            Answer a4 = new Answer();
            a4.setText("Языком разметки");
            a4.setQuestion(q);
            a4.setCorrect(false);
            answerRepository.save(a4);
            Answer a5 = new Answer();
            a5.setText("int");
            a5.setQuestion(q2);
            a5.setCorrect(false);
            answerRepository.save(a5);
            Answer a6 = new Answer();
            a6.setText("String");
            a6.setQuestion(q2);
            a6.setCorrect(true);
            answerRepository.save(a6);
            Answer a7 = new Answer();
            a7.setText("double");
            a7.setQuestion(q2);
            a7.setCorrect(false);
            answerRepository.save(a7);
            Answer a8 = new Answer();
            a8.setText("boolean");
            a8.setQuestion(q2);
            a8.setCorrect(false);
            answerRepository.save(a8);

        }

        if (userRepository.findByUsername("admin").isEmpty()) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(roleAdmin);
            admin.setCreateDate(LocalDateTime.now());
            userRepository.save(admin);
        }
    }
}
