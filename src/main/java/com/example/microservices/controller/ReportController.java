package com.example.microservices.controller;

import com.example.microservices.model.Report;
import com.example.microservices.service.ReportService;
import com.example.microservices.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/public-transport/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;
    @PostMapping
    public ResponseEntity<String> addReport(@RequestBody Report report) {
        if (report.getDescription().isEmpty()) {
            return ResponseEntity.status(400).body("Report must contain description");
        } else if (report.getDelay() == null) {
            report.setDelay(0);
        }
        reportService.addReport(report);
        return ResponseEntity.status(201).body("Report Added");
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updateReport(@PathVariable Long id, @RequestBody Report report){
        if (!reportService.updateReport(id, report)) {
            return ResponseEntity.status(404).body("Could not find report " + id);
        } else if (report.getDescription() == null){
            return ResponseEntity.status(400).body("Check correct description input");
        } else if (report.getDescription().isEmpty()) {
            return ResponseEntity.status(400).body("Description can not be empty");
        } else {
            return ResponseEntity.status(201).body("Updated report");
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Report>> getReports(){
        List<Report> reportList = reportService.getAllReports();
        if (reportList.isEmpty()){
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(reportList);
        }
    }

}
