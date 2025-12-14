package ru.egor.testingapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.egor.testingapp.entity.Result;
import ru.egor.testingapp.service.HistoryService;

import java.util.List;

/**
 * Контроллер для обработки веб-запросов, связанных с историей результатов пользователя.
 * Предоставляет эндпоинты для просмотра истории результатов тестов текущего пользователя.
 */
@Controller
public class HistoryWebController {

    private final HistoryService historyService;

    @Autowired
    public HistoryWebController(HistoryService historyService) {
        this.historyService = historyService;
    }

    /**
     * Отображает историю результатов для текущего пользователя.
     * Получает результаты из сервиса истории и добавляет их в модель для отображения в представлении.
     *
     * @param model модель, в которую добавляются результаты
     * @return имя представления для отображения (в данном случае, "history")
     */
    @GetMapping("/history")
    public String viewHistory(Model model) {

        List<Result> results = historyService.getResultsForCurrentUser();

        model.addAttribute("results", results);

        return "history";
    }

}
