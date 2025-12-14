package ru.egor.testingapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.egor.testingapp.service.ProfileService;

/**
 * Контроллер для обработки веб-запросов, связанных с профилем пользователя.
 * Предоставляет эндпоинты для просмотра профиля, обновления данных пользователя и управления тестами.
 */
@Controller
@RequestMapping("/profile")
public class ProfileWebController {

    private final ProfileService profileService;

    public ProfileWebController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * Отображает страницу профиля пользователя.
     * Добавляет в модель текущего пользователя и связанные с ним тесты для отображения в представлении.
     *
     * @param model модель, в которую добавляются атрибуты для использования в представлении
     * @return имя шаблона представления для отображения (в данном случае, "profile")
     */
    @GetMapping()
    public String profile(Model model) {
        model.addAttribute("user", profileService.getCurrentUser());
        model.addAttribute("tests", profileService.getTestsOfCurrentUser());
        return "profile";
    }

    /**
     * Обновляет имя пользователя.
     * Если имя успешно обновлено, добавляет успешное сообщение в flash-атрибуты.
     * Если возникает исключение (например, имя уже занято), добавляет сообщение об ошибке.
     *
     * @param username имя пользователя для обновления
     * @param ra       объект для добавления flash-атрибутов
     * @return перенаправление на страницу профиля
     */
    @PostMapping("/update-name")
    public String updateName(
            @RequestParam String username,
            RedirectAttributes ra,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            profileService.updateUsername(username);

            new SecurityContextLogoutHandler()
                    .logout(request, response, null);

            ra.addFlashAttribute("error",
                    "Имя изменено. Пожалуйста, войдите заново.");
            ra.addFlashAttribute("messageType", "info");

            return "redirect:/login";

        } catch (Exception e) {
            ra.addFlashAttribute("message", "Ошибка: имя уже занято");
            ra.addFlashAttribute("messageType", "error");
            return "redirect:/profile";
        }
    }

    /**
     * Обновляет пароль пользователя.
     * После успешного обновления пароля добавляет сообщение об успехе в flash-атрибуты.
     *
     * @param password новый пароль для обновления
     * @param ra       объект для добавления flash-атрибутов
     * @return перенаправление на страницу профиля
     */
    @PostMapping("/update-password")
    public String updatePassword(
            @RequestParam String password,
            RedirectAttributes ra,
            HttpServletRequest request,
            HttpServletResponse response) {

        profileService.updatePassword(password);

        new SecurityContextLogoutHandler()
                .logout(request, response, null);

        ra.addFlashAttribute("error",
                "Пароль успешно изменён. Пожалуйста, войдите заново.");
        ra.addFlashAttribute("messageType", "info");

        return "redirect:/login";
    }

    /**
     * Удаляет тест пользователя по его ID.
     * После успешного удаления добавляет сообщение об успехе в flash-атрибуты.
     *
     * @param id идентификатор теста для удаления
     * @param ra объект для добавления flash-атрибутов
     * @return перенаправление на страницу профиля
     */
    @PostMapping("/delete-test/{id}")
    public String deleteTest(@PathVariable Long id, RedirectAttributes ra) {
        profileService.deleteTest(id);
        ra.addFlashAttribute("message", "Тест удалён");
        ra.addFlashAttribute("messageType", "success");
        return "redirect:/profile";
    }
}

