package ru.egor.testingapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.egor.testingapp.configuration.ReportStatus;
import ru.egor.testingapp.entity.Report;
import ru.egor.testingapp.entity.Test;
import ru.egor.testingapp.repository.ReportRepository;
import ru.egor.testingapp.repository.TestRepository;
import ru.egor.testingapp.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class ReportService {

    private final UserRepository userRepository;
    private final TestRepository testRepository;
    private final ReportRepository reportRepository;

    @Autowired
    public ReportService(TestRepository testRepository, ReportRepository reportRepository, UserRepository userRepository) {
        this.testRepository = testRepository;
        this.reportRepository = reportRepository;
        this.userRepository = userRepository;
    }

    public Long createReport() {
        Report report = new Report(ReportStatus.CREATED, "Отчет формируется...");
        reportRepository.save(report);
        return report.getId();
    }

    public String getReport(Long id) {
        Report report = reportRepository.findById(id).orElse(null);
        if (report == null) {
            return "Отчет не найден";
        }
        if (report.getStatus() == ReportStatus.CREATED) {
            return "Отчет еще формируется...";
        }
        if (report.getStatus() == ReportStatus.ERROR) {
            return "Ошибка при формировании отчета..";
        }
        return report.getBody();
    }

    public CompletableFuture<Void> generateReport(Long id) {
        return CompletableFuture.runAsync(() -> {
            long startTime = System.currentTimeMillis();
            Report report = reportRepository.findById(id).orElseThrow();
            try {
                //1 поток
                AtomicLong userCount = new AtomicLong();
                AtomicLong elapsedUserCountTime = new AtomicLong();
                Thread userCountThread = new Thread(() -> {
                    long startUserCountTime = System.currentTimeMillis();
                    userCount.set(userRepository.count());
                    elapsedUserCountTime.set(System.currentTimeMillis() - startUserCountTime);
                });

                //2 поток
                List<Test> listTest = new ArrayList<>();
                AtomicLong elapsedTestCountTime = new AtomicLong();
                Thread testCountThread = new Thread(() -> {
                    long startTestCountTime = System.currentTimeMillis();
                    Iterable<Test> tests = testRepository.findAll();
                    tests.forEach(listTest::add);
                    elapsedTestCountTime.set(System.currentTimeMillis() - startTestCountTime);
                });

                userCountThread.start();
                testCountThread.start();

                userCountThread.join();
                testCountThread.join();

                StringBuilder rb = new StringBuilder();
                rb.append("<html><head><meta charset='UTF-8'><title>Практическая работа №6.</title></head><body>");
                rb.append("<h2>Отчет сформирован успешно!</h2>");
                rb.append("<p>Количество пользователей: ").append(userCount).append("</p>");
                rb.append("Список тестов: ");
                rb.append("<table border='1' cellpadding='5'><tr><th>ID</th><th>Название</th><th>Описание</th><th>Автор</th><th>Дата создания</th></tr>");
                for (Test test : listTest) {
                    rb.append("<tr>")
                            .append("<td>").append(test.getId()).append("</td>")
                            .append("<td>").append(test.getTitle()).append("</td>")
                            .append("<td>").append(test.getDescription()).append("</td>")
                            .append("<td>").append(test.getAuthor()).append("</td>")
                            .append("<td>").append(test.getCreateDate()).append("</td>")
                            .append("</tr>");
                }
                rb.append("</table>");
                long totalElapsedTime = System.currentTimeMillis() - startTime;
                rb.append("<p>Время подсчета пользователей: ").append(elapsedUserCountTime).append(" мс. </p>");
                rb.append("<p>Время получения списка тестов: ").append(elapsedTestCountTime).append(" мс. </p>");
                rb.append("<p>Общее время формирования отчета: ").append(totalElapsedTime).append(" мс. </p>");
                rb.append("</body></html>");

                report.setStatus(ReportStatus.COMPLETED);
                report.setBody(rb.toString());
                reportRepository.save(report);
            } catch (Exception e) {
                report.setStatus(ReportStatus.ERROR);
                report.setBody("Ошибка при формировании отчета: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }
}
