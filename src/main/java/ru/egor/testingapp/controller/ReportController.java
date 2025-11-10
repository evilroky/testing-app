package ru.egor.testingapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.egor.testingapp.service.ReportService;

@Controller
@RequestMapping("/api/reports")
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public String createReport() {
        Long id = reportService.createReport();
        reportService.generateReport(id);
        return "redirect:/api/reports/" + id;
    }

    @GetMapping("/{id}")
    @ResponseBody
    public String getReports(@PathVariable Long id) {
        return reportService.getReport(id);
    }

    @GetMapping
    public String getReports() {
        return "report";
    }

}
