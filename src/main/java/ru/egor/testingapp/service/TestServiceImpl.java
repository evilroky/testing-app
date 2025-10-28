package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.egor.testingapp.entity.Question;
import ru.egor.testingapp.repository.QuestionRepository;
import ru.egor.testingapp.repository.TestRepository;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {

    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public TestServiceImpl(TestRepository testRepository, QuestionRepository questionRepository, PlatformTransactionManager transactionManager) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void deleteTest(String title) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try {
            List<Question> questions = questionRepository.findByTestsTitle(title);
            for (Question question : questions) {
                questionRepository.delete(question);
            }

            testRepository.deleteByTitle(title);

            transactionManager.commit(status);
        } catch (DataAccessException e) {

            transactionManager.rollback(status);
            throw e;
        }
    }
}
