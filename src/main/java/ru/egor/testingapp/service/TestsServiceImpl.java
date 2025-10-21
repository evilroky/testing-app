package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import ru.egor.testingapp.entity.Questions;
import ru.egor.testingapp.repository.QuestionsRepository;
import ru.egor.testingapp.repository.TestsRepository;

import java.util.List;

@Service
public class TestsServiceImpl implements TestsService {

    private final TestsRepository testsRepository;
    private final QuestionsRepository questionsRepository;
    private final PlatformTransactionManager transactionManager;

    @Autowired
    public TestsServiceImpl(TestsRepository testsRepository, QuestionsRepository questionsRepository, PlatformTransactionManager transactionManager) {
        this.testsRepository = testsRepository;
        this.questionsRepository = questionsRepository;
        this.transactionManager = transactionManager;
    }

    @Override
    public void deleteTests(String title) {
        TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
        try{
            List<Questions> questions = questionsRepository.findByTestsTitle(title);
            for (Questions question : questions) {
                questionsRepository.delete(question);
            }

            testsRepository.deleteByTitle(title);

            transactionManager.commit(status);
        }
        catch(DataAccessException e){

            transactionManager.rollback(status);
            throw e;
        }
    }
}
