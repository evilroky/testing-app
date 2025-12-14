package ru.egor.testingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.egor.testingapp.entity.Tests;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.TestRepository;
import ru.egor.testingapp.repository.UserRepository;
import ru.egor.testingapp.service.ProfileService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProfileServiceTests {

    @Mock
    private TestRepository testRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private ProfileService profileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    void getCurrentUser_WhenUserExists_ShouldReturnUser() {
        String username = "testUser";
        User expectedUser = new User();
        expectedUser.setUsername(username);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        User result = profileService.getCurrentUser();

        assertEquals(expectedUser, result);
        verify(userRepository).findByUsername(username);
    }

    @Test
    void getCurrentUser_WhenUserNotFound_ShouldThrowRuntimeException() {
        String username = "nonExistentUser";

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            profileService.getCurrentUser();
        });

        assertEquals("Пользователь не найден", exception.getMessage());
        verify(userRepository).findByUsername(username);
    }

    @Test
    void getTestsOfCurrentUser_WhenUserHasTests_ShouldReturnTests() {
        String username = "testUser";
        User currentUser = new User();
        currentUser.setId(1L);
        List<Tests> expectedTests = List.of(new Tests(), new Tests());

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(currentUser));
        when(testRepository.findAllByAuthor_IdOrderByCreateDateDesc(1L)).thenReturn(expectedTests);

        List<Tests> result = profileService.getTestsOfCurrentUser();

        assertEquals(expectedTests, result);
        verify(testRepository).findAllByAuthor_IdOrderByCreateDateDesc(1L);
    }

    @Test
    void updateUsername_WhenValidName_ShouldUpdateAndSaveUser() {
        String username = "testUser";
        String newName = "newTestUser";
        User currentUser = new User();
        currentUser.setUsername(username);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(currentUser));

        profileService.updateUsername(newName);

        assertEquals(newName, currentUser.getUsername());
        verify(userRepository).save(currentUser);
    }

    @Test
    void updatePassword_WhenValidPassword_ShouldEncodeAndSaveUser() {
        String username = "testUser";
        String newPassword = "newPassword";
        String encodedPassword = "encodedPassword";
        User currentUser = new User();
        currentUser.setUsername(username);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(currentUser));
        when(passwordEncoder.encode(newPassword)).thenReturn(encodedPassword);

        profileService.updatePassword(newPassword);

        assertEquals(encodedPassword, currentUser.getPassword());
        verify(userRepository).save(currentUser);
        verify(passwordEncoder).encode(newPassword);
    }

    @Test
    void deleteTest_WhenTestIdProvided_ShouldDeleteTest() {
        Long testId = 1L;

        profileService.deleteTest(testId);

        verify(testRepository).deleteById(testId);
    }
}
