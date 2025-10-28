package ru.egor.testingapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.entity.Question;
import ru.egor.testingapp.entity.Test;
import ru.egor.testingapp.repository.QuestionRepository;
import ru.egor.testingapp.repository.TestRepository;
import ru.egor.testingapp.service.TestServiceImpl;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Transactional
public class TestServiceTest {

    private final QuestionRepository questionRepository;
    private final TestRepository testRepository;
    private final TestServiceImpl testsService;

    @Autowired
    TestServiceTest(QuestionRepository questionRepository, TestRepository testRepository, TestServiceImpl testsService) {
        this.questionRepository = questionRepository;
        this.testRepository = testRepository;
        this.testsService = testsService;
    }

    @BeforeEach
    void clearDatabaseBF() {
        questionRepository.deleteAll();
        testRepository.deleteAll();
    }

    @AfterEach
    void clearDatabaseAF() {
        questionRepository.deleteAll();
        testRepository.deleteAll();
    }

    @org.junit.jupiter.api.Test
    void testTestsService() {
        Test test = new Test();
        String title = UUID.randomUUID().toString();
        test.setTitle(title);
        testRepository.save(test);

        Question questions1 = new Question();
        questions1.setTest_id(test);
        questions1.setText(UUID.randomUUID().toString());
        questionRepository.save(questions1);

        Question questions2 = new Question();
        questions2.setTest_id(test);
        questions2.setText(UUID.randomUUID().toString());
        questionRepository.save(questions2);

        testsService.deleteTest(title);

        Optional<Test> optionalTests = testRepository.findById(test.getId());
        Assertions.assertTrue(optionalTests.isEmpty());

        Optional<Question> optionalQuestions1 = questionRepository.findById(questions1.getId());
        Assertions.assertTrue(optionalQuestions1.isEmpty());

        Optional<Question> optionalQuestions2 = questionRepository.findById(questions2.getId());
        Assertions.assertTrue(optionalQuestions2.isEmpty());
    }
}
