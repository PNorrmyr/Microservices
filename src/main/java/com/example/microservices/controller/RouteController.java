package com.example.microservices.controller;


import com.example.microservices.model.Report;
import com.example.microservices.service.ReportService;
import com.example.microservices.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public-transport")
public class RouteController {
    @Autowired
    private ReportService reportService;
    @Autowired
    private RouteService routeService;


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

    @PatchMapping("/reports/{id}")
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

    @GetMapping("/reports")
    public Optional<List<Report>> getReports(){
        return reportService.getAllReports();
    }

}
