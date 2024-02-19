package com.example.microservices.controller;


import com.example.microservices.model.ErrorReport;
import com.example.microservices.service.ErrorReportService;
import com.example.microservices.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/public-transport")
public class RouteController {
    @Autowired
    private ErrorReportService errorReportService;
    @Autowired
    private RouteService routeService;


    @PostMapping
    public ResponseEntity<String> addReport(@RequestBody ErrorReport errorReport){
        if (errorReport.getDescription().isEmpty()){
            return ResponseEntity.status(400).body("Report must contain description");
        } else {
        errorReportService.addReport(errorReport);
        return ResponseEntity.status(201).body("Report Added");
        }
    }

    @PatchMapping("/report/{id}")
    public ResponseEntity<String> updateReport(@PathVariable Long id, @RequestBody ErrorReport errorReport){
        if (!errorReportService.updateReport(id, errorReport)) {
            return ResponseEntity.status(404).body("Could not find report " + id);
        } else if (errorReport.getDescription() == null){
            return ResponseEntity.status(400).body("Check correct description input");
        } else if (errorReport.getDescription().isEmpty()) {
            return ResponseEntity.status(400).body("Description can not be empty");
        } else {
            return ResponseEntity.status(201).body("Updated report");
        }
    }
}
