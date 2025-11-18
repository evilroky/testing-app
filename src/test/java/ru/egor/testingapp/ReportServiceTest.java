package ru.egor.testingapp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.egor.testingapp.configuration.ReportStatus;
import ru.egor.testingapp.entity.Report;
import ru.egor.testingapp.repository.ReportRepository;
import ru.egor.testingapp.repository.TestRepository;
import ru.egor.testingapp.repository.UserRepository;
import ru.egor.testingapp.service.ReportService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ReportServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private TestRepository testRepository;

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportService reportService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateReportSuccess() {
        Report saved = new Report(ReportStatus.CREATED, "Отчет формируется...");
        saved.setId(10L);

        when(reportRepository.save(any(Report.class))).thenAnswer(inv -> {
            Report r = inv.getArgument(0);
            r.setId(10L);
            return r;
        });

        Long id = reportService.createReport();

        assertEquals(10L, id);
        verify(reportRepository, times(1)).save(any());
    }

    @Test
    void testGetReportNotFound() {
        when(reportRepository.findById(1L)).thenReturn(Optional.empty());

        String res = reportService.getReport(1L);

        assertEquals("Отчет не найден", res);
    }

    @Test
    void testGetReportCreatedStatus() {
        Report r = new Report(ReportStatus.CREATED, null);

        when(reportRepository.findById(1L)).thenReturn(Optional.of(r));

        String res = reportService.getReport(1L);

        assertEquals("Отчет еще формируется...", res);
    }

    @Test
    void testGetReportErrorStatus() {
        Report r = new Report(ReportStatus.ERROR, null);

        when(reportRepository.findById(1L)).thenReturn(Optional.of(r));

        String res = reportService.getReport(1L);

        assertEquals("Ошибка при формировании отчета..", res);
    }

    @Test
    void testGetReportCompletedStatus() {
        Report r = new Report(ReportStatus.COMPLETED, "HTML");

        when(reportRepository.findById(1L)).thenReturn(Optional.of(r));

        String res = reportService.getReport(1L);

        assertEquals("HTML", res);
    }
}
