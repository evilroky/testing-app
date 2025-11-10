package ru.egor.testingapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TestingAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestingAppApplication.class, args);
    }

}
