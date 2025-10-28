package ru.egor.testingapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.criteria.QuestionsRepositoryCustom;
import ru.egor.testingapp.entity.Question;
import ru.egor.testingapp.entity.Test;
import ru.egor.testingapp.repository.QuestionRepository;
import ru.egor.testingapp.repository.TestRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Transactional
public class QuestionRepositoryTest {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final QuestionsRepositoryCustom questionsRepositoryCustom;

    @Autowired
    QuestionRepositoryTest(TestRepository testRepository, QuestionRepository questionRepository, QuestionsRepositoryCustom questionsRepositoryCustom) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.questionsRepositoryCustom = questionsRepositoryCustom;
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
    void testFindQuestionByTestsTitle() {
        String title = UUID.randomUUID().toString();
        Test test = new Test();
        test.setTitle(title);
        testRepository.save(test);

        Question questions = new Question();
        questions.setTest_id(test);
        questions.setText(UUID.randomUUID().toString());
        List<Question> questionsList = new ArrayList<>();
        questionsList.add(questions);
        questionRepository.save(questions);


        List<Question> foundQuestions = questionRepository.findByTestsTitle(title);

        Assertions.assertNotNull(foundQuestions);
        Assertions.assertEquals(questionsList.getFirst().getId(), foundQuestions.getFirst().getId());
        Assertions.assertEquals(questionsList.getFirst().getText(), foundQuestions.getFirst().getText());
    }

    @org.junit.jupiter.api.Test
    void testFindQuestionsByTestsTitleCustom() {
        String title = UUID.randomUUID().toString();
        Test test = new Test();
        test.setTitle(title);
        testRepository.save(test);

        Question questions = new Question();
        questions.setTest_id(test);
        questions.setText(UUID.randomUUID().toString());
        List<Question> questionsList = new ArrayList<>();
        questionsList.add(questions);
        questionsRepositoryCustom.save(questions);


        List<Question> foundQuestions = questionsRepositoryCustom.findByTestsTitle(title);

        Assertions.assertNotNull(foundQuestions);
        Assertions.assertEquals(questionsList.getFirst().getId(), foundQuestions.getFirst().getId());
        Assertions.assertEquals(questionsList.getFirst().getText(), foundQuestions.getFirst().getText());

    }
}
