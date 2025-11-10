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

    public Long reportCreate() {
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
        return report.getTitle();
    }

    public CompletableFuture<Void> generateReport(Long id) {
        return CompletableFuture.runAsync(() -> {
            long start = System.currentTimeMillis();
            Report report = reportRepository.findById(id).orElseThrow();
            try {
                //1 поток
                final long[] userCount = new long[1];
                final long[] elapsedUserCount = new long[1];
                Thread userCountThread = new Thread(() -> {
                    long startUserCount = System.currentTimeMillis();
                    userCount[0] = userRepository.count();
                    elapsedUserCount[0] = System.currentTimeMillis() - startUserCount;
                });

                //2 поток
                List<Test> listTest = new ArrayList<>();
                final long[] elapsedTestCount = new long[1];
                final long startTestCount = System.currentTimeMillis();
                Thread testCountThread = new Thread(() -> {
                    Iterable<Test> tests = testRepository.findAll();
                    tests.forEach(listTest::add);
                    elapsedTestCount[0] = System.currentTimeMillis() - startTestCount;
                });

                userCountThread.start();
                testCountThread.start();

                userCountThread.join();
                testCountThread.join();

                StringBuilder rb = new StringBuilder();
                rb.append("<html><head><meta charset='UTF-8'><title>Практическая работа №6.</title></head><body>");
                rb.append("<h2>Отчет сформирован успешно!</h2>");
                rb.append("<p>Количество пользователей: ").append(userCount[0]).append("</p>");
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
                long totalElapsedTime = System.currentTimeMillis() - start;
                rb.append("<p>Время подсчета пользователей: ").append(elapsedUserCount[0]).append(" мс. </p>");
                rb.append("<p>Время получения списка тестов: ").append(elapsedTestCount[0]).append(" мс. </p>");
                rb.append("<p>Общее время формирования отчета: ").append(totalElapsedTime).append(" мс. </p>");
                rb.append("</body></html>");

                report.setStatus(ReportStatus.COMPLETED);
                report.setTitle(rb.toString());
                reportRepository.save(report);
            } catch (Exception e) {
                report.setStatus(ReportStatus.ERROR);
                report.setTitle("Ошибка при формировании отчета: " + e.getMessage());
                reportRepository.save(report);
            }
        });
    }
}
