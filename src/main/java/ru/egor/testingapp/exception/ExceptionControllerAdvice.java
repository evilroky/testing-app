package ru.egor.testingapp.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Класс ExceptionControllerAdvice предоставляет обработку различных типов исключений в приложении.
 * Используется для перехвата и обработки ошибок на уровне контроллеров.
 */
@ControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * Обрабатывает общие исключения (500 Internal Server Error).
     * Добавляет сообщение об ошибке в flash-атрибуты и перенаправляет на главную страницу.
     *
     * @param e  объект исключения
     * @param ra объект для добавления flash-атрибутов
     * @return строка перенаправления на главную страницу
     */
    @ExceptionHandler(java.lang.Exception.class)
    public String exception(java.lang.Exception e, RedirectAttributes ra) {
        ra.addFlashAttribute("messageType", "error");
        ra.addFlashAttribute("message", "Произошла непредвиденная ошибка: " + e.getMessage());
        return "redirect:/";
    }

    /**
     * Обрабатывает исключения ResourceNotFoundException (404 Not Found).
     * Добавляет сообщение о ненайденной странице и перенаправляет на главную.
     *
     * @param e  объект исключения ResourceNotFoundException
     * @param ra объект для добавления flash-атрибутов
     * @return строка перенаправления на главную страницу
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public String exception(ResourceNotFoundException e, RedirectAttributes ra) {
        ra.addFlashAttribute("messageType", "error");
        ra.addFlashAttribute("message", "Страница не найдена!");
        return "redirect:/";
    }

    /**
     * Обрабатывает исключения MethodArgumentNotValidException (400 Bad Request).
     * Добавляет сообщение о неверном значении переменной и перенаправляет на главную.
     *
     * @param e  объект исключения MethodArgumentNotValidException
     * @param ra объект для добавления flash-атрибутов
     * @return строка перенаправления на главную страницу
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public String exception(MethodArgumentNotValidException e, RedirectAttributes ra) {
        ra.addFlashAttribute("messageType", "error");
        ra.addFlashAttribute("message", "Не удается проверить значение переменной!");
        return "redirect:/";
    }

    /**
     * Обрабатывает исключения HttpRequestMethodNotSupportedException (405 Method Not Allowed).
     * Добавляет сообщение о недоступности страницы и перенаправляет на главную.
     *
     * @param e  объект исключения HttpRequestMethodNotSupportedException
     * @param ra объект для добавления flash-атрибутов
     * @return строка перенаправления на главную страницу
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String exception(HttpRequestMethodNotSupportedException e, RedirectAttributes ra) {
        ra.addFlashAttribute("messageType", "error");
        ra.addFlashAttribute("message", "Нет доступа к странице!");
        return "redirect:/";
    }

    /**
     * Обрабатывает исключения IllegalArgumentException (400 Bad Request).
     * Добавляет сообщение о неверном аргументе и перенаправляет на главную.
     *
     * @param e  объект исключения IllegalArgumentException
     * @param ra объект для добавления flash-атрибутов
     * @return строка перенаправления на главную страницу
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String exception(IllegalArgumentException e, RedirectAttributes ra) {
        ra.addFlashAttribute("messageType", "error");
        ra.addFlashAttribute("message", "Не верный агрумент!");
        return "redirect:/";
    }

    /**
     * Обрабатывает исключения DataIntegrityViolationException (409 Conflict).
     * Добавляет сообщение о нарушении целостности базы данных и перенаправляет на главную.
     *
     * @param e  объект исключения DataIntegrityViolationException
     * @param ra объект для добавления flash-атрибутов
     * @return строка перенаправления на главную страницу
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public String exception(DataIntegrityViolationException e, RedirectAttributes ra) {
        ra.addFlashAttribute("messageType", "error");
        ra.addFlashAttribute("message", "Операция нарушает целостность базы данных!");
        return "redirect:/";
    }

    /**
     * Обрабатывает исключения AccessDeniedException (403 Forbidden).
     * Добавляет сообщение о запрете доступа и перенаправляет на главную.
     *
     * @param e  объект исключения AccessDeniedException
     * @param ra объект для добавления flash-атрибутов
     * @return строка перенаправления на главную страницу
     */
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException e, RedirectAttributes ra) {
        ra.addFlashAttribute("messageType", "error");
        ra.addFlashAttribute("message", "У вас нет прав для доступа к этой странице!");
        return "redirect:/";
    }
}
