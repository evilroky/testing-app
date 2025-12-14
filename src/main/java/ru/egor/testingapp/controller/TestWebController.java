package ru.egor.testingapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.egor.testingapp.entity.Answer;
import ru.egor.testingapp.entity.Question;
import ru.egor.testingapp.entity.Result;
import ru.egor.testingapp.entity.Tests;
import ru.egor.testingapp.service.CustomUserDetailsService;
import ru.egor.testingapp.service.TestService;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для управления тестами в веб-приложении.
 * Обрабатывает создание, отображение, редактирование и отправку тестов, а также показ результатов тестирования.
 */
@Controller
@RequestMapping("/tests")
public class TestWebController {

    private final TestService testService;

    @Autowired
    public TestWebController(TestService testService) {
        this.testService = testService;
    }

    /**
     * Открывает страницу создания нового теста.
     * Возвращает имя представления "create-test" для отображения формы создания теста.
     *
     * @return имя представления для страницы создания теста
     */
    @GetMapping("/create")
    public String getCreateTestPage() {
        return "create-test";
    }

    /**
     * Создает новый тест на основе данных из HTTP-запроса.
     * Если тест успешно создан, добавляет сообщение об успехе в flash-атрибуты.
     * В случае ошибки при создании теста, добавляет сообщение об ошибке.
     *
     * @param request            HTTP-запрос, содержащий данные для создания теста
     * @param redirectAttributes атрибуты перенаправления для передачи сообщений пользователю
     * @return перенаправление на страницу создания теста
     */
    @PostMapping("/create")
    public String createTest(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        try {
            testService.createTest(request);
            redirectAttributes.addFlashAttribute("message", "Тест успешно создан!");
            redirectAttributes.addFlashAttribute("messageType", "success");

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("message", "Ошибка при создании теста!");
            redirectAttributes.addFlashAttribute("messageType", "error");
        }

        return "redirect:/tests/create";
    }

    /**
     * Отображает список тестов, при необходимости отфильтрованный по теме.
     * Заполняет модель всеми доступными темами, выбранной темой, списком тестов
     * и количеством вопросов для каждого теста.
     *
     * @param theme тема для фильтрации тестов, или null для отображения всех тестов
     * @param model модель для заполнения атрибутов представления
     * @return имя шаблона представления для отображения ("tests")
     */
    @GetMapping
    public String showTests(@RequestParam(value = "theme", required = false) String theme,
                            Model model) {

        List<String> themes = testService.getAllThemes();
        List<Tests> tests = testService.getTestsByTheme(theme);

        Map<Long, Integer> questionCount = new HashMap<>();
        for (Tests t : tests) {
            questionCount.put(
                    t.getId(),
                    testService.countQuestionsByTestId(t.getId())
            );
        }

        model.addAttribute("themes", themes);
        model.addAttribute("tests", tests);
        model.addAttribute("selectedTheme", theme);
        model.addAttribute("questionCount", questionCount);
        return "tests";
    }

    /**
     * Отображает страницу теста по его ID.
     * Получает тест, вопросы и ответы для отображения на странице,
     * добавляет их в модель для передачи в представление.
     *
     * @param id    идентификатор теста для отображения
     * @param model модель для добавления атрибутов
     * @return имя шаблона представления для отображения страницы теста
     */
    @GetMapping("/{id}")
    public String showTestPage(@PathVariable Long id, Model model) {

        List<Question> questions = testService.getQuestionsByTestId(id);

        Map<Long, List<Answer>> answersMap = new HashMap<>();
        for (Question q : questions) {
            answersMap.put(q.getId(), testService.getAnswersByQuestionId(q.getId()));
        }

        model.addAttribute("test", testService.getTestById(id));
        model.addAttribute("questions", questions);
        model.addAttribute("answersMap", answersMap);

        return "test-page";
    }

    /**
     * Обрабатывает отправку ответов на тест.
     * Проверяет ответы пользователя, вычисляет результат и сохраняет его.
     * Перенаправляет пользователя на страницу с результатами теста.
     *
     * @param id                 идентификатор теста
     * @param request            HTTP-запрос, содержащий ответы пользователя
     * @param redirectAttributes атрибуты перенаправления для передачи данных о результате
     * @return строка перенаправления на страницу с результатами теста
     */
    @PostMapping("/{id}/submit")
    public String submitTest(
            @PathVariable Long id,
            HttpServletRequest request,
            RedirectAttributes redirectAttributes
    ) {
        int score = testService.checkTestAnswers(id, request);
        int total = testService.getTotalQuestions(id);

        testService.saveResult(id, score, total);

        redirectAttributes.addFlashAttribute("score", score);
        redirectAttributes.addFlashAttribute("total", total);

        return "redirect:/tests/" + id + "/result";
    }

    /**
     * Отображает страницу с результатами теста по его ID.
     * Получает тест и последние результаты по нему, добавляет их в модель для отображения в представлении.
     *
     * @param id    идентификатор теста для отображения результатов
     * @param model модель для добавления атрибутов
     * @return имя шаблона представления для отображения страницы с результатами теста
     */
    @GetMapping("/{id}/result")
    public String showResult(@PathVariable Long id, Model model) {

        Tests tests = testService.getTestById(id);
        Result result = testService.getLatestResult(id);

        model.addAttribute("test", tests);
        model.addAttribute("result", result);

        return "test-result";
    }

    /**
     * Отображает страницу редактирования теста для указанного ID теста.
     * Получает тест, его вопросы и соответствующие ответы,
     * а затем добавляет их в модель для отображения на странице редактирования.
     *
     * @param id    идентификатор теста для редактирования
     * @param model модель для заполнения данными теста
     * @return имя представления для отображения (edit-test)
     */
    @GetMapping("/edit/{id}")
    public String editTestPage(@PathVariable Long id, Model model, Principal principal) {
        Tests tests = testService.getTestById(id);
        if (!tests.getAuthor().getUsername().equals(principal.getName())) {
            throw new AccessDeniedException("Вы не автор теста");
        }
        Map<Long, List<Answer>> answersByQuestion = testService.getAnswersByQuestionForTest(id);

        model.addAttribute("test", tests);
        model.addAttribute("questions", testService.getQuestionsByTest(id));
        model.addAttribute("answers", answersByQuestion);

        return "edit-test";
    }

    /**
     * Сохраняет отредактированный тест.
     * Обновляет данные теста, обрабатывает удаление вопросов и возвращает пользователю сообщение о результате.
     *
     * @param id                 идентификатор теста для обновления
     * @param title              новое название теста
     * @param theme              новая тема теста
     * @param description        новое описание теста (опционально)
     * @param deleteQuestions    строка с ID вопросов для удаления (опционально)
     * @param request            HTTP-запрос, содержащий данные для обновления
     * @param redirectAttributes атрибуты перенаправления для передачи сообщений пользователю
     * @return строка перенаправления на страницу профиля
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
        return "redirect:/profile";
    }
}
