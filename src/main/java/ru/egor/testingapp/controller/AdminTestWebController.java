package ru.egor.testingapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.egor.testingapp.entity.Answer;
import ru.egor.testingapp.entity.Tests;
import ru.egor.testingapp.service.AdminService;
import ru.egor.testingapp.service.TestService;

import java.util.List;
import java.util.Map;

/**
 * Контроллер для управления тестами в админ-панели.
 * Предоставляет эндпоинты для списка, редактирования, сохранения и удаления тестов.
 */
@Controller
@RequestMapping("/admin/tests")
public class AdminTestWebController {

    private final AdminService adminService;
    private final TestService testService;

    @Autowired
    public AdminTestWebController(AdminService adminService, TestService testService) {
        this.adminService = adminService;
        this.testService = testService;
    }

    /**
     * Обрабатывает GET-запрос для отображения списка тестов.
     * Добавляет список всех тестов в модель и возвращает имя представления для страницы администратора тестов.
     *
     * @param model модель, в которую будет добавлен список тестов
     * @return имя представления для отображения (admin-test)
     */
    @GetMapping
    public String listTests(Model model) {
        model.addAttribute("tests", testService.getAllTests());
        return "admin-test";
    }

    /**
     * Отображает страницу редактирования теста для указанного ID теста.
     * Получает тест, его вопросы и соответствующие ответы,
     * а затем добавляет их в модель для отображения на странице редактирования.
     *
     * @param id    идентификатор теста для редактирования
     * @param model модель для заполнения данными теста
     * @return имя представления для отображения (admin-edit-test)
     */
    @GetMapping("/edit/{id}")
    public String editTestPage(@PathVariable Long id, Model model) {
        Tests tests = testService.getTestById(id);
        Map<Long, List<Answer>> answersByQuestion = testService.getAnswersByQuestionForTest(id);

        model.addAttribute("test", tests);
        model.addAttribute("questions", testService.getQuestionsByTest(id));
        model.addAttribute("answers", answersByQuestion);

        return "admin-edit-test";
    }

    /**
     * Сохраняет отредактированный тест с указанным ID.
     * Обновляет тест, используя предоставленные параметры и обрабатывает любые исключения, возникающие во время обновления.
     * Перенаправляет на страницу администратора с сообщением об успешном выполнении или ошибке.
     *
     * @param id                 идентификатор теста для обновления
     * @param title              название теста
     * @param theme              тема теста
     * @param description        описание теста (необязательно)
     * @param deleteQuestions    идентификаторы вопросов для удаления (необязательно)
     * @param request            HTTP-запрос, содержащий дополнительные параметры
     * @param redirectAttributes атрибуты перенаправления для передачи сообщений
     * @return URL перенаправления на страницу тестов администратора
     */
    @PostMapping("/edit/{id}")
    public String saveEditedTest(@PathVariable Long id,
                                 @RequestParam String title,
                                 @RequestParam String theme,
                                 @RequestParam(required = false) String description,
                                 @RequestParam(required = false, name = "deleteQuestions") String deleteQuestions,
                                 HttpServletRequest request,
                                 RedirectAttributes redirectAttributes) {
        try {
            testService.updateTest(id, title, theme, description, deleteQuestions, request);
            redirectAttributes.addFlashAttribute("message", "Тест успешно обновлён");
            redirectAttributes.addFlashAttribute("messageType", "success");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Ошибка при сохранении теста");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }
        return "redirect:/admin/tests";
    }

    /**
     * Удаляет тест по его ID и перенаправляет на страницу администратора с тестами.
     *
     * @param id ID теста для удаления
     * @return строка перенаправления на страницу администратора с тестами
     */
    @PostMapping("/delete/{id}")
    public String deleteTest(@PathVariable Long id) {
        testService.deleteById(id);
        return "redirect:/admin/tests";
    }
}
