package ru.egor.testingapp;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginLogoutTest {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeEach
    public void setUp() {

        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginAndLogout() throws InterruptedException {

        driver.get("http://localhost:" + port + "/login");


        WebElement usernameInput = driver.findElement(By.name("username"));
        WebElement passwordInput = driver.findElement(By.name("password"));


        usernameInput.sendKeys("admin");
        passwordInput.sendKeys("admin");


        WebElement loginButton = driver.findElement(By.cssSelector("input[type='submit']"));
        loginButton.click();


        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("logout"), "После входа должна отображаться домашняя страница");


        WebElement logoutButton = driver.findElement(By.cssSelector("button[type='submit']"));
        logoutButton.click();


        Thread.sleep(1000);
        assertTrue(driver.getPageSource().contains("Имя пользователя"), "После выхода должна отображаться страница входа");
    }
}

