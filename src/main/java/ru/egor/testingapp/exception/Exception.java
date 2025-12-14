package ru.egor.testingapp.exception;

/**
 * Класс Exception представляет собой пользовательский класс исключений для обработки ошибок в приложении.
 * Предоставляет функционал для создания исключений как из строки, так и из объекта Throwable.
 */
public class Exception {
    /**
     * Сообщение об ошибке
     */
    private String message;

    /**
     * Конструктор класса Exception.
     *
     * @param message сообщение об ошибке
     */
    private Exception(String message) {
        this.message = message;
    }

    /**
     * Создает экземпляр Exception из объекта Throwable.
     *
     * @param e объект Throwable
     * @return новый экземпляр Exception
     */
    public static Exception create(Throwable e) {
        return new Exception(e.getMessage());
    }

    /**
     * Создает экземпляр Exception из строки.
     *
     * @param message сообщение об ошибке
     * @return новый экземпляр Exception
     */
    public static Exception create(String message) {
        return new Exception(message);
    }

    /**
     * Возвращает сообщение об ошибке.
     *
     * @return сообщение об ошибке
     */
    public String getMessage() {
        return message;
    }

    /**
     * Устанавливает новое сообщение об ошибке.
     *
     * @param message новое сообщение об ошибке
     */
    public void setMessage(String message) {
        this.message = message;
    }
}

