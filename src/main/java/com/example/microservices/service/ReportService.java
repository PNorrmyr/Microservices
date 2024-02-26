package com.example.microservices.service;

import com.example.microservices.model.Report.Report;
import com.example.microservices.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ReportService {

    @Autowired
    private ReportRepository reportRepository;


    public void addReport(Report report){
        reportRepository.save(report);
    }

    public Optional<Report> searchById(Long id){
        return reportRepository.findById(id);
    }

    public boolean updateReport(Long id, Report report){
        Optional<Report> optionalReport = searchById(id);
        if (optionalReport.isEmpty()){
            return false;
        } else {
            Report existingReport = optionalReport.get();
            existingReport.setDescription(report.getDescription());
            existingReport.setDelay(report.getDelay());
            reportRepository.save(existingReport);
        }
       return true;
    }

    public List<Report> getAllReports(){
        return reportRepository.findAll();
    }


}
