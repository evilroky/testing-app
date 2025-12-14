package ru.egor.testingapp;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import ru.egor.testingapp.configuration.SpringInit;
import ru.egor.testingapp.repository.RoleRepository;
import ru.egor.testingapp.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthTest {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Autowired
    public AuthTest(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    protected WebDriver driver;
    protected String baseUrl;

    @LocalServerPort
    protected int serverPort;

    @Autowired
    private SpringInit springInit;

    @BeforeEach
    public void setUp() throws Exception {

        springInit.run();

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        baseUrl = "http://localhost:" + serverPort;
    }

    @AfterEach
    public void tearDown() {

        userRepository.deleteAll();
        roleRepository.deleteAll();

        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testPositiveLogin() {
        driver.get(baseUrl);

        driver.findElement(By.id("login")).click();

        driver.findElement(By.name("username")).sendKeys("user");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.id("loginButton")).click();

        WebElement greeting = driver.findElement(By.id("name"));
        assertEquals("Привет, user!", greeting.getText());

        driver.findElement(By.id("logoutButton")).click();
    }

    @Test
    public void testNegativeLogin() {
        driver.get(baseUrl);

        driver.findElement(By.id("login")).click();

        driver.findElement(By.name("username")).sendKeys("wronguser");
        driver.findElement(By.name("password")).sendKeys("wrongpass");
        driver.findElement(By.id("loginButton")).click();

        WebElement error = driver.findElement(By.id("error"));
        assertEquals("Неверный логин или пароль", error.getText());

    }

    @Test
    public void testPositiveRegistrationAndLogin() {
        driver.get(baseUrl);

        driver.findElement(By.id("registration")).click();

        driver.findElement(By.name("username")).sendKeys("newuser");
        driver.findElement(By.name("password")).sendKeys("newpass");
        driver.findElement(By.id("registerButton")).click();

        assertEquals(userRepository.findByUsername("newuser").orElseThrow().getUsername(),"newuser");

        driver.findElement(By.name("username")).sendKeys("newuser");
        driver.findElement(By.name("password")).sendKeys("newpass");
        driver.findElement(By.id("loginButton")).click();

        // Проверка приветственного сообщения
        WebElement greeting = driver.findElement(By.id("name"));
        assertEquals("Привет, newuser!", greeting.getText());

        driver.findElement(By.id("logoutButton")).click();
    }

    @Test
    public void testNegativeRegistrationExistingUser() {
        driver.get(baseUrl);

        driver.findElement(By.id("registration")).click();

        driver.findElement(By.name("username")).sendKeys("user");
        driver.findElement(By.name("password")).sendKeys("123");
        driver.findElement(By.id("registerButton")).click();

        WebElement error = driver.findElement(By.cssSelector(".error-message span"));
        assertEquals("Ошибка регистрации: пользователь уже существует", error.getText());

    }
}
