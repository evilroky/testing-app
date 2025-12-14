package ru.egor.testingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.egor.testingapp.entity.Result;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.ResultRepository;
import ru.egor.testingapp.repository.UserRepository;
import ru.egor.testingapp.service.HistoryService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistoryServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ResultRepository resultRepository;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private HistoryService historyService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getResultsForCurrentUser_ShouldReturnResults_WhenUserExists() {
        String username = "testUser";
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername(username);

        Result result1 = new Result();
        Result result2 = new Result();
        List<Result> expectedResults = List.of(result1, result2);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(resultRepository.findAllByPassedBy_IdOrderByPassedDateDesc(userId)).thenReturn(expectedResults);

        List<Result> actualResults = historyService.getResultsForCurrentUser();

        assertEquals(expectedResults, actualResults);
        verify(userRepository).findByUsername(username);
        verify(resultRepository).findAllByPassedBy_IdOrderByPassedDateDesc(userId);
    }

    @Test
    void getResultsForCurrentUser_ShouldThrowException_WhenUserNotFound() {
        String username = "nonExistentUser";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            historyService.getResultsForCurrentUser();
        });

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(userRepository).findByUsername(username);
        verify(resultRepository, never()).findAllByPassedBy_IdOrderByPassedDateDesc(anyLong());
    }
}
