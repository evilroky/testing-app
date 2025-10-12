package ru.egor.testingapp.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.egor.testingapp.service.TestService;

import java.util.Scanner;

@Configuration
public class ConsoleRunnerConfig {

    @Autowired
    private CommandProcessor commandProcessor;

    @Bean
    public CommandLineRunner commandScanner(TestService testService) {
        return (args) -> {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Введите команду. 'exit для выхода.'");
            while (true) {
                System.out.print("> ");
                if (!scanner.hasNextLine()) {
                    System.out.println("Ввод завершён. Программа закрывается.");
                    break;
                }
                String input = scanner.nextLine();
                if ("exit".equalsIgnoreCase(input.trim())) {
                    System.out.println("Выход из программы...");
                    break;
                }
                commandProcessor.processCommand(input);
            }
        };
    }
}
