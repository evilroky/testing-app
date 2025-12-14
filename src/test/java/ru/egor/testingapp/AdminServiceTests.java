package ru.egor.testingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.egor.testingapp.entity.Role;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.RoleRepository;
import ru.egor.testingapp.repository.UserRepository;
import ru.egor.testingapp.service.AdminService;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AdminServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        adminService.getAllUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById() {
        long userId = 1L;
        adminService.getUserById(userId);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void updateUser_UsernameChanged() {
        Long userId = 1L;
        Long roleId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("oldUsername");
        existingUser.setPassword("oldPassword");

        Role role = new Role();
        role.setId(roleId);

        User updatedUser = new User();
        updatedUser.setUsername("newUsername");
        updatedUser.setPassword("oldPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        adminService.updateUser(userId, updatedUser, roleId);

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findById(roleId);
        verify(userRepository, times(1)).save(existingUser);
        assertEquals("newUsername", existingUser.getUsername());
        assertEquals("oldPassword", existingUser.getPassword());
    }

    @Test
    void updateUser_PasswordChanged() {
        Long userId = 1L;
        Long roleId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("username");
        existingUser.setPassword("oldPassword");

        Role role = new Role();
        role.setId(roleId);

        User updatedUser = new User();
        updatedUser.setUsername("username");
        updatedUser.setPassword("newPassword");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        adminService.updateUser(userId, updatedUser, roleId);

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findById(roleId);
        verify(userRepository, times(1)).save(existingUser);
        assertEquals("username", existingUser.getUsername());
        assertNotEquals("newPassword", existingUser.getPassword());
        assertTrue(new BCryptPasswordEncoder().matches("newPassword", existingUser.getPassword()));
    }

    @Test
    void updateUser_RoleChanged() {
        Long userId = 1L;
        Long roleId = 2L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("username");
        existingUser.setPassword("password");

        Role oldRole = new Role();
        oldRole.setId(1L);
        existingUser.setRole(oldRole);

        Role newRole = new Role();
        newRole.setId(roleId);

        User updatedUser = new User();
        updatedUser.setUsername("username");
        updatedUser.setPassword("password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(newRole));

        adminService.updateUser(userId, updatedUser, roleId);

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findById(roleId);
        verify(userRepository, times(1)).save(existingUser);
        assertEquals(newRole, existingUser.getRole());
    }

    @Test
    void updateUser_NoChanges() {
        Long userId = 1L;
        Long roleId = 1L;
        User existingUser = new User();
        existingUser.setId(userId);
        existingUser.setUsername("username");
        existingUser.setPassword("password");

        Role role = new Role();
        role.setId(roleId);
        existingUser.setRole(role);

        User updatedUser = new User();
        updatedUser.setUsername("username");
        updatedUser.setPassword("password");

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        adminService.updateUser(userId, updatedUser, roleId);

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findById(roleId);
        verify(userRepository, times(1)).save(existingUser);
        assertEquals("username", existingUser.getUsername());
        assertEquals("password", existingUser.getPassword());
        assertEquals(role, existingUser.getRole());
    }

    @Test
    void updateUser_UserNotFound() {
        Long userId = 1L;
        Long roleId = 1L;
        User updatedUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> {
            adminService.updateUser(userId, updatedUser, roleId);
        });

        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, never()).findById(anyLong());
        verify(userRepository, never()).save(any());
    }

    @Test
    void updateUser_RoleNotFound() {
        Long userId = 1L;
        Long roleId = 1L;
        User existingUser = new User();
        User updatedUser = new User();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(roleRepository.findById(roleId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            adminService.updateUser(userId, updatedUser, roleId);
        });

        assertEquals("Роль не найдена", exception.getMessage());
        verify(userRepository, times(1)).findById(userId);
        verify(roleRepository, times(1)).findById(roleId);
        verify(userRepository, never()).save(any());
    }

    @Test
    void deleteUser() {
        Long userId = 1L;
        adminService.deleteUser(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void getAllRoles() {
        adminService.getAllRoles();
        verify(roleRepository, times(1)).findAll();
    }
}
