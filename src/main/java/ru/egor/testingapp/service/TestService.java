package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.egor.testingapp.entity.TestEntity;
import ru.egor.testingapp.repository.TestRepository;

import java.util.List;
import java.util.Scanner;

@Service
public class TestService implements FunctionalTestService{

     private final TestRepository repository;

     @Autowired
     public TestService(TestRepository repository) {
         this.repository = repository;
         TestEntity t1 = new TestEntity(1L, "Основы Java", "Программирование");
         t1.addQuestion("Что такое JVM?|виртуальная машина");
         t1.addQuestion("Что такое класс?|шаблон");
         repository.create(t1);
     }

     @Override
     public void createTest(Long id, String title, String subject){
         TestEntity t1 = new TestEntity(id, title, subject);
         repository.create(t1);
     }

    @Override
    public TestEntity findById(Long id) {
         return repository.read(id);
    }

    @Override
    public void deleteById(Long id) {
        repository.delete(id);
    }

    @Override
    public void updateTitle(Long id, String newTitle) {
        TestEntity t1 = repository.read(id);
        if(t1 != null){
            t1.setTitle(newTitle);
            repository.update(t1);
        }
    }

    @Override
    public List<TestEntity> listAll() {
        return repository.findAll();
    }

    @Override
    public int takeTest(Long id) {
        TestEntity test = repository.read(id);
        if(test == null){
            System.out.println("Тест с id=" + id + " не найден.");
            return 0;
        }
        if (test.getQuestions().isEmpty()){
            System.out.println("В тесте нет вопросов.");
            return 0;
        }

        try (Scanner scanner = new Scanner(System.in)) {
            int total = test.getQuestions().size();
            int correct = 0;
            System.out.println("Начинаем тест: " + test.getTitle());
            for (String qa: test.getQuestions()) {
                String[] parts = qa.split("\\|");
                String question = parts[0];
                String answer = parts.length > 1 ? parts[1] : "";
                System.out.println("Вопрос: " + question);
                System.out.println("Ваш ответ: ");
                String user = scanner.nextLine().trim().toLowerCase();
                if(!answer.isEmpty() && user.contains(answer.toLowerCase())){
                    correct++;
                }
            }
            int score = (int) Math.round(100.0 * correct / total);
            System.out.println("Результат: " + correct + " из " + total + " - оценка: " + score + "%");
            return score;
        }
    }
}
