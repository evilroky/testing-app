package ru.egor.testingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.egor.testingapp.entity.User;
import ru.egor.testingapp.service.AdminService;

/**
 * Контроллер для управления пользователями в админ-панели.
 * Предоставляет эндпоинты для просмотра, редактирования и удаления пользователей.
 */
@Controller
@RequestMapping("/admin/users")
public class AdminUsersWebController {

    private final AdminService adminService;

    @Autowired
    public AdminUsersWebController(AdminService adminService) {
        this.adminService = adminService;
    }

    /**
     * Открывает страницу администратора и добавляет в модель список всех пользователей.
     *
     * @param model модель для добавления данных о пользователях
     * @return имя представления для отображения (admin-user)
     */
    @GetMapping
    public String openAdminPage(Model model) {
        model.addAttribute("users", adminService.getAllUsers());
        return "admin-user";
    }

    /**
     * Открывает страницу редактирования пользователя с указанным ID.
     * Добавляет в модель пользователя и все доступные роли для отображения в представлении.
     *
     * @param id    идентификатор пользователя для редактирования
     * @param model модель для добавления атрибутов
     * @return имя представления для отображения (admin-edit-user)
     */
    @GetMapping("/edit/{id}")
    public String openEditUserPage(@PathVariable Long id, Model model) {
        model.addAttribute("user", adminService.getUserById(id));
        model.addAttribute("roles", adminService.getAllRoles());
        return "admin-edit-user";
    }

    /**
     * Обрабатывает отправку формы редактирования пользователя.
     * Обновляет пользователя с указанным ID, используя предоставленные данные пользователя и ID роли.
     *
     * @param id     идентификатор пользователя для обновления
     * @param user   объект пользователя, содержащий обновленные данные
     * @param roleId идентификатор роли, которую нужно назначить пользователю
     * @return строка перенаправления на страницу пользователей администратора
     */
    @PostMapping("/edit/{id}")
    public String processEditUserForm(@PathVariable Long id, @ModelAttribute User user, @RequestParam("roleId") Long roleId) {
        adminService.updateUser(id, user, roleId);
        return "redirect:/admin/users";
    }

    /**
     * Обрабатывает удаление пользователя по его ID.
     * Вызывает сервис администратора для удаления пользователя и перенаправляет на страницу со списком пользователей.
     *
     * @param id ID пользователя для удаления
     * @return строка перенаправления на страницу пользователей администратора
     */
    @PostMapping("/delete/{id}")
    public String processDeleteUserForm(@PathVariable Long id) {
        adminService.deleteUser(id);
        return "redirect:/admin/users";
    }
}
