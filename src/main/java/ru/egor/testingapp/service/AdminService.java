package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.egor.testingapp.entity.Role;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.repository.RoleRepository;
import ru.egor.testingapp.repository.UserRepository;

import java.util.List;

/**
 * Сервис AdminService предоставляет функционал для управления пользователями и ролями в административной части приложения.
 * Обеспечивает работу с данными пользователей и их ролей.
 */
@Service
public class AdminService {

    /**
     * Репозиторий для работы с пользователями
     */
    private final UserRepository userRepository;

    /**
     * Репозиторий для работы с ролями
     */
    private final RoleRepository roleRepository;

    /**
     * Конструктор сервиса.
     *
     * @param userRepository репозиторий пользователей
     * @param roleRepository репозиторий ролей
     */
    @Autowired
    public AdminService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    /**
     * Возвращает список всех пользователей.
     *
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Возвращает пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     * @return пользователь с указанным идентификатором
     */
    public User getUserById(long id) {
        return userRepository.findById(id);
    }

    /**
     * Обновляет информацию о пользователе.
     *
     * @param id     идентификатор пользователя
     * @param user   объект пользователя с обновленными данными
     * @param roleId идентификатор роли пользователя
     */
    @Transactional
    public void updateUser(Long id, User user, Long roleId) {
        User user1 = userRepository.findById(id).orElseThrow();
        if (!user.getUsername().equals(user1.getUsername())) {
            user1.setUsername(user.getUsername());
        }
        if (!user.getPassword().equals(user1.getPassword()) && !user.getPassword().isEmpty()) {
            String password = user.getPassword();
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            password = passwordEncoder.encode(password);
            user1.setPassword(password);
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Роль не найдена"));
        if (!role.equals(user1.getRole())) {
            user1.setRole(role);
        }

        userRepository.save(user1);
    }

    /**
     * Удаляет пользователя по его идентификатору.
     *
     * @param id идентификатор пользователя
     */
    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    /**
     * Возвращает список всех ролей.
     *
     * @return список всех ролей
     */
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }
}

