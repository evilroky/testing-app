package ru.egor.testingapp.service;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.entity.*;
import ru.egor.testingapp.repository.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.StreamSupport;

/**
 * Сервис TestService предоставляет функционал для создания и управления тестами.
 * Обеспечивает создание тестов с вопросами и ответами, а также взаимодействие с репозиториями.
 *
 * @author Egor
 * @version 1.0
 */
@Service
public class TestService {


    private final TestRepository testRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final ResultRepository resultRepository;

    @Autowired
    public TestService(TestRepository testRepository, QuestionRepository questionRepository, AnswerRepository answerRepository, UserRepository userRepository, ResultRepository resultRepository) {
        this.testRepository = testRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    /**
     * Создает новый тест на основе данных из запроса.
     *
     * @param request HTTP запрос содержащий данные теста
     */
    public void createTest(HttpServletRequest request) {
        String title = request.getParameter("title");
        String theme = request.getParameter("theme");
        String description = request.getParameter("description");
        User author = userRepository.findByUsername(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ).orElseThrow();

        Tests tests = new Tests();
        tests.setTitle(title);
        tests.setTheme(theme);
        tests.setDescription(description);
        tests.setAuthor(author);
        tests.setCreateDate(LocalDateTime.now());

        testRepository.save(tests);

        int questionIndex = 0;

        while (true) {
            String questionText = request.getParameter("questions[" + questionIndex + "].text");

            if (questionText == null) {
                break;
            }

            Question question = new Question();
            question.setText(questionText);
            question.setTest(tests);
            questionRepository.save(question);

            for (int a = 0; a < 4; a++) {

                String answerText = request.getParameter("questions[" + questionIndex + "].answers[" + a + "].text");

                if (answerText == null) continue;

                boolean correct = request.getParameter("questions[" + questionIndex + "].answers[" + a + "].correct") != null;

                Answer ans = new Answer();
                ans.setQuestion(question);
                ans.setText(answerText);
                ans.setCorrect(correct);
                answerRepository.save(ans);
            }

            questionIndex++;
        }
    }

    /**
     * Возвращает список всех уникальных тем тестов, отсортированных по возрастанию.
     *
     * @return список тем тестов
     */
    public List<String> getAllThemes() {
        return testRepository.findAllThemesSorted();
    }

    /**
     * Возвращает список тестов по теме.
     *
     * @param theme тема теста
     * @return список тестов с указанной темой или все тесты, если тема не указана
     */
    public List<Tests> getTestsByTheme(String theme) {
        if (theme == null || theme.isBlank()) {
            return StreamSupport.stream(testRepository.findAll().spliterator(), false)
                    .toList();
        }
        return testRepository.findByTheme(theme);
    }

    /**
     * Подсчитывает количество вопросов для теста по его идентификатору.
     *
     * @param testId идентификатор теста
     * @return количество вопросов в тесте
     */
    public int countQuestionsByTestId(Long testId) {
        return questionRepository.countByTestsId(testId);
    }

    /**
     * Возвращает список вопросов для теста по его идентификатору.
     *
     * @param id идентификатор теста
     * @return список вопросов теста
     */
    public List<Question> getQuestionsByTestId(Long id) {
        return questionRepository.findByTestsId(id);
    }

    /**
     * Возвращает общее количество вопросов в тесте.
     *
     * @param id идентификатор теста
     * @return количество вопросов в тесте
     */
    public int getTotalQuestions(Long id) {
        return questionRepository.countByTestsId(id);
    }

    /**
     * Проверяет ответы пользователя на тест.
     *
     * @param testId  идентификатор теста
     * @param request HTTP запрос с ответами пользователя
     * @return количество правильных ответов
     */
    public int checkTestAnswers(Long testId, HttpServletRequest request) {
        List<Question> questions = questionRepository.findByTestsId(testId);
        int score = 0;

        for (Question q : questions) {
            String answerIdStr = request.getParameter("q_" + q.getId());
            if (answerIdStr == null) continue;

            Answer answer = answerRepository.findById(Long.valueOf(answerIdStr))
                    .orElse(null);

            if (answer != null && Boolean.TRUE.equals(answer.getCorrect())) {
                score++;
            }
        }

        return score;
    }

    /**
     * Сохраняет результат прохождения теста.
     *
     * @param testId идентификатор теста
     * @param score  количество правильных ответов
     * @param total  общее количество вопросов
     */
    public void saveResult(Long testId, int score, int total) {
        Result r = new Result();
        r.setPassedDate(LocalDateTime.now());
        r.setTest(testRepository.findById(testId).get());
        r.setScore(score);
        r.setTotal(total);

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElse(null);
        r.setPassedBy(user);

        resultRepository.save(r);
    }

    /**
     * Возвращает список ответов для вопроса по его идентификатору.
     *
     * @param qId идентификатор вопроса
     * @return список ответов на вопрос
     */
    public List<Answer> getAnswersByQuestionId(Long qId) {
        return answerRepository.findByQuestionId(qId);
    }

    /**
     * Возвращает последний результат прохождения теста пользователем.
     *
     * @param testId идентификатор теста
     * @return последний результат прохождения теста
     */
    public Result getLatestResult(Long testId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        Result r = resultRepository.findFirstByPassedBy_IdAndTests_IdOrderByPassedDateDesc(
                user.getId(), testId
        );

        if (r == null)
            throw new RuntimeException("Результатов для этого теста нет!");

        return r;
    }

    /**
     * Возвращает тест по его идентификатору.
     *
     * @param id идентификатор теста
     * @return объект теста
     */
    public Tests getTestById(Long id) {
        return testRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден"));
    }

    /**
     * Возвращает список вопросов для теста по его идентификатору.
     *
     * @param testId идентификатор теста
     * @return список вопросов теста
     */
    public List<Question> getQuestionsByTest(Long testId) {
        return questionRepository.findByTestsId(testId);
    }

    /**
     * Возвращает отображение ответов для вопросов теста.
     *
     * @param testId идентификатор теста
     * @return отображение {идентификатор вопроса: список ответов}
     */
    public Map<Long, List<Answer>> getAnswersByQuestionForTest(Long testId) {
        List<Question> questions = questionRepository.findByTestsId(testId);
        Map<Long, List<Answer>> map = new HashMap<>();
        for (Question q : questions) {
            map.put(q.getId(), answerRepository.findByQuestion(q));
        }
        return map;
    }

    /**
     * Обновляет тест с указанными параметрами.
     *
     * @param testId          идентификатор теста
     * @param title           новое название теста
     * @param theme           новая тема теста
     * @param description     новое описание теста
     * @param deleteQuestions строка с идентификаторами вопросов для удаления
     * @param request         HTTP запрос с данными для обновления
     */
    @Transactional
    public void updateTest(Long testId,
                           String title,
                           String theme,
                           String description,
                           String deleteQuestions,
                           HttpServletRequest request) {

        Tests tests = testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Тест не найден"));

        tests.setTitle(title);
        tests.setTheme(theme);
        tests.setDescription(description);
        testRepository.save(tests);

        List<Question> questions = questionRepository.findByTestsId(testId);

        for (Question q : questions) {
            String qText = request.getParameter("question_" + q.getId());
            if (qText != null) {
                q.setText(qText);
                questionRepository.save(q);
            }

            List<Answer> answers = answerRepository.findByQuestion(q);
            for (Answer a : answers) {
                String aText = request.getParameter("answer_" + a.getId());
                String isCorrect = request.getParameter("correct_" + a.getId());

                if (aText != null) {
                    a.setText(aText);
                }
                a.setCorrect(isCorrect != null);
                answerRepository.save(a);
            }
        }

        if (deleteQuestions != null && !deleteQuestions.trim().isEmpty()) {
            String[] ids = deleteQuestions.split(",");
            for (String idStr : ids) {
                if (idStr.isBlank()) continue;
                Long qId = Long.parseLong(idStr.trim());

                Question q = questionRepository.findById(qId)
                        .orElse(null);
                if (q != null) {

                    List<Answer> answers = answerRepository.findByQuestion(q);
                    answerRepository.deleteAll(answers);

                    questionRepository.delete(q);
                }
            }
        }

        Map<String, String[]> params = request.getParameterMap();
        for (String paramName : params.keySet()) {
            if (paramName.startsWith("new_question_")) {
                String idx = paramName.substring("new_question_".length());
                String qText = request.getParameter(paramName);

                if (qText == null || qText.isBlank()) continue;

                Question q = new Question();
                q.setTest(tests);
                q.setText(qText);
                questionRepository.save(q);


                for (int i = 1; i <= 4; i++) {
                    String aName = "new_answer_" + idx + "_" + i;
                    String cName = "new_correct_" + idx + "_" + i;

                    String aText = request.getParameter(aName);
                    String isCorrect = request.getParameter(cName);

                    if (aText != null && !aText.isBlank()) {
                        Answer a = new Answer();
                        a.setQuestion(q);
                        a.setText(aText);
                        a.setCorrect(isCorrect != null);
                        answerRepository.save(a);
                    }
                }
            }
        }
    }

    /**
     * Возвращает список всех тестов.
     *
     * @return список всех тестов
     */
    public List<Tests> getAllTests() {
        return testRepository.findAll();
    }

    /**
     * Удаляет тест по его идентификатору.
     *
     * @param id идентификатор теста
     */
    public void deleteById(Long id) {
        testRepository.deleteById(id);
    }
}
