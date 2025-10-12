package ru.egor.testingapp.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.egor.testingapp.entity.TestEntity;
import ru.egor.testingapp.service.TestService;

import java.util.List;

@Component
public class CommandProcessor {

    private final TestService testService;

    @Autowired
    public CommandProcessor(final TestService testService) {
        this.testService = testService;
    }

    public void processCommand(String input) {
        String[] cmd = input.trim().split("\\s+", 2);
        String action = cmd[0].toLowerCase();
        String args = cmd.length > 1 ? cmd[1] : "";

        try{
            switch (action) {
                case "create" -> {
                    String[] parts = args.split(";",3);
                    Long id = Long.valueOf(parts[0].trim());
                    testService.createTest(id,parts[1].trim(), parts[2].trim());
                    System.out.println("Тест добавлен.");
                }
                case "read" -> {
                    Long id = Long.valueOf(args.trim());
                    TestEntity t = testService.findById(id);
                    if (t == null) {
                        System.out.println("Не найдено.");
                    }else {
                        System.out.println("ID: " + t.getId() + " Title: " + t.getTitle() + " Subject: " + t.getSubject());
                        if(!t.getQuestions().isEmpty()) {
                            System.out.println("Вопросы.");
                            t.getQuestions().forEach(q -> System.out.println(" - " + q));
                        }
                    }
                }
                case "update" -> {
                    String[] parts = args.split(";",2);
                    Long id = Long.valueOf(parts[0].trim());
                    testService.updateTitle(id,parts[1].trim());
                    System.out.println("Заголовок обновлен.");
                }
                case "delete" -> {
                    Long id = Long.valueOf(args.trim());
                    testService.deleteById(id);
                    System.out.println("Удалено. ");
                }
                case "list" -> {
                    List<TestEntity> all = testService.listAll();
                    if (all.isEmpty()) {
                        System.out.println("Список пустю");
                    }else {
                        all.forEach(t -> System.out.println(t.getId() + "|" + t.getTitle() + "|" + t.getSubject()));
                    }
                }
                case "addq" -> {
                    String[] parts = args.split(";",3);
                    Long id = Long.valueOf(parts[0].trim());
                    TestEntity t = testService.findById(id);
                    if (t == null) {
                        System.out.println("Тест не найден.");
                    } else {
                        t.addQuestion(parts[1].trim() + "|" + parts[2].trim());
                        testService.updateTitle(id, t.getTitle());
                        System.out.println("Вопрос добавлен.");
                    }
                }
                case "take" -> {
                    Long id = Long.valueOf(args.trim());
                    testService.takeTest(id);
                    System.out.println("Тест выполнен! Ура!!");
                }
                default -> {
                    System.out.println("Неизвестная команда.");
                }
            }
        }catch (Exception e) {
            System.out.println("Ошибка при выполнении команды: " + e.getMessage());
        }
    }
}
