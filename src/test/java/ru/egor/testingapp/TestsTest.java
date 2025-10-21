package ru.egor.testingapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.egor.testingapp.entity.Questions;
import ru.egor.testingapp.entity.Tests;
import ru.egor.testingapp.repository.QuestionsRepository;
import ru.egor.testingapp.repository.TestsRepository;
import ru.egor.testingapp.service.TestsServiceImpl;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class TestsTest {

    private final QuestionsRepository questionsRepository;
    private final TestsRepository testsRepository;
    private final TestsServiceImpl testsService;

    @Autowired
    TestsTest(QuestionsRepository questionsRepository, TestsRepository testsRepository, TestsServiceImpl testsService) {
        this.questionsRepository = questionsRepository;
        this.testsRepository = testsRepository;
        this.testsService = testsService;
    }

    @Test
    void testTestsService() {
        Tests tests = new Tests();
        String title = UUID.randomUUID().toString();
        tests.setTitle(title);
        testsRepository.save(tests);

        Questions questions1 = new Questions();
        questions1.setTest_id(tests);
        questions1.setText(UUID.randomUUID().toString());
        questionsRepository.save(questions1);

        Questions questions2 = new Questions();
        questions2.setTest_id(tests);
        questions2.setText(UUID.randomUUID().toString());
        questionsRepository.save(questions2);

        testsService.deleteTests(title);

        Optional<Tests> optionalTests = testsRepository.findById(tests.getId());
        Assertions.assertTrue(optionalTests.isEmpty());

        Optional<Questions> optionalQuestions1 = questionsRepository.findById(questions1.getId());
        Assertions.assertTrue(optionalQuestions1.isEmpty());

        Optional<Questions> optionalQuestions2 = questionsRepository.findById(questions2.getId());
        Assertions.assertTrue(optionalQuestions2.isEmpty());
    }
}
