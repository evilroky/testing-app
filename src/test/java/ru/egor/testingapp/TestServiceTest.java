package ru.egor.testingapp;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.egor.testingapp.entity.*;
import ru.egor.testingapp.repository.*;
import ru.egor.testingapp.service.TestService;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;

class TestServiceTest {

    @InjectMocks
    private TestService testService;

    @Mock
    private TestRepository testRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private AnswerRepository answerRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    private User mockUser;
    private Tests mockTest;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        mockUser = new User();
        mockUser.setId(1L);
        mockUser.setUsername("testUser");

        when(authentication.getName()).thenReturn("testUser");
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(mockUser));

        mockTest = new Tests();
        mockTest.setId(1L);
        mockTest.setTitle("Test Title");
        mockTest.setTheme("Test Theme");
        mockTest.setDescription("Test Description");
        mockTest.setAuthor(mockUser);
        mockTest.setCreateDate(LocalDateTime.now());
    }

    @Test
    void createTest_ShouldCreateTestWithQuestionsAndAnswers() {
        when(request.getParameter("title")).thenReturn("Test Title");
        when(request.getParameter("theme")).thenReturn("Test Theme");
        when(request.getParameter("description")).thenReturn("Test Description");

        when(request.getParameter("questions[0].text")).thenReturn("Question 1");
        when(request.getParameter("questions[0].answers[0].text")).thenReturn("Answer 1");
        when(request.getParameter("questions[0].answers[0].correct")).thenReturn("on");
        when(request.getParameter("questions[0].answers[1].text")).thenReturn("Answer 2");
        when(request.getParameter("questions[0].answers[1].correct")).thenReturn(null);
        when(request.getParameter("questions[0].answers[2].text")).thenReturn("Answer 3");
        when(request.getParameter("questions[0].answers[2].correct")).thenReturn(null);
        when(request.getParameter("questions[0].answers[3].text")).thenReturn("Answer 4");
        when(request.getParameter("questions[0].answers[3].correct")).thenReturn(null);

        when(request.getParameter("questions[1].text")).thenReturn(null);

        testService.createTest(request);

        verify(testRepository, times(1)).save(any(Tests.class));
        verify(questionRepository, times(1)).save(any(Question.class));
        verify(answerRepository, times(4)).save(any(Answer.class));
    }

    @Test
    void createTest_ShouldHandleNoQuestions() {
        when(request.getParameter("title")).thenReturn("Test Title");
        when(request.getParameter("theme")).thenReturn("Test Theme");
        when(request.getParameter("description")).thenReturn("Test Description");
        when(request.getParameter("questions[0].text")).thenReturn(null);

        testService.createTest(request);

        verify(testRepository, times(1)).save(any(Tests.class));
        verify(questionRepository, never()).save(any(Question.class));
        verify(answerRepository, never()).save(any(Answer.class));
    }

    @Test
    void createTest_ShouldHandleMissingAnswers() {
        when(request.getParameter("title")).thenReturn("Test Title");
        when(request.getParameter("theme")).thenReturn("Test Theme");
        when(request.getParameter("description")).thenReturn("Test Description");

        when(request.getParameter("questions[0].text")).thenReturn("Question 1");
        when(request.getParameter("questions[0].answers[0].text")).thenReturn("Answer 1");
        when(request.getParameter("questions[0].answers[0].correct")).thenReturn("on");
        when(request.getParameter("questions[0].answers[1].text")).thenReturn("Answer 2");
        when(request.getParameter("questions[0].answers[1].correct")).thenReturn(null);
        when(request.getParameter("questions[0].answers[2].text")).thenReturn(null);

        when(request.getParameter("questions[1].text")).thenReturn(null);

        testService.createTest(request);

        verify(testRepository, times(1)).save(any(Tests.class));
        verify(questionRepository, times(1)).save(any(Question.class));
        verify(answerRepository, times(2)).save(any(Answer.class));
    }
}
