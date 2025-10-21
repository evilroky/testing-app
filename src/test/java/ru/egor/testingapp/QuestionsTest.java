package ru.egor.testingapp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.egor.testingapp.criteria.QuestionsRepositoryCustom;
import ru.egor.testingapp.entity.Questions;
import ru.egor.testingapp.entity.Tests;
import ru.egor.testingapp.repository.QuestionsRepository;
import ru.egor.testingapp.repository.TestsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
public class QuestionsTest {

    private final TestsRepository testsRepository;
    private final QuestionsRepository questionsRepository;
    private final QuestionsRepositoryCustom questionsRepositoryCustom;

    @Autowired
    QuestionsTest(TestsRepository testsRepository, QuestionsRepository questionsRepository, QuestionsRepositoryCustom questionsRepositoryCustom) {
        this.testsRepository = testsRepository;
        this.questionsRepository = questionsRepository;
        this.questionsRepositoryCustom = questionsRepositoryCustom;
    }

    @Test
    void testFindQuestionByTestsTitle(){
        String title = UUID.randomUUID().toString();
        Tests tests = new Tests();
        tests.setTitle(title);
        testsRepository.save(tests);

        Questions questions = new Questions();
        questions.setTest_id(tests);
        questions.setText(UUID.randomUUID().toString());
        List<Questions> questionsList = new ArrayList<>();
        questionsList.add(questions);
        questionsRepository.save(questions);


        List<Questions> foundQuestions = questionsRepository.findByTestsTitle(title);

        Assertions.assertNotNull(foundQuestions);
        Assertions.assertEquals(questionsList.getFirst().getId(), foundQuestions.getFirst().getId());
        Assertions.assertEquals(questionsList.getFirst().getText(), foundQuestions.getFirst().getText());
    }

    @Test
    void testFindQuestionsByTestsTitleCustom() {
        String title = UUID.randomUUID().toString();
        Tests tests = new Tests();
        tests.setTitle(title);
        testsRepository.save(tests);

        Questions questions = new Questions();
        questions.setTest_id(tests);
        questions.setText(UUID.randomUUID().toString());
        List<Questions> questionsList = new ArrayList<>();
        questionsList.add(questions);
        questionsRepositoryCustom.save(questions);


        List<Questions> foundQuestions = questionsRepositoryCustom.findByTestsTitle(title);

        Assertions.assertNotNull(foundQuestions);
        Assertions.assertEquals(questionsList.getFirst().getId(), foundQuestions.getFirst().getId());
        Assertions.assertEquals(questionsList.getFirst().getText(), foundQuestions.getFirst().getText());

    }
}
