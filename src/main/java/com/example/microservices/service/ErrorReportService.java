package com.example.microservices.service;

import com.example.microservices.model.ErrorReport;
import com.example.microservices.repository.ErrorReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ErrorReportService {

    @Autowired
    private ErrorReportRepository errorReportRepository;


    public void addReport(ErrorReport errorReport){
        errorReportRepository.save(errorReport);
    }

    public Optional<ErrorReport> searchById(Long id){
        return errorReportRepository.findById(id);
    }

    public boolean updateReport(Long id, ErrorReport errorReport){
        Optional<ErrorReport> optionalErrorReport = searchById(id);
        if (optionalErrorReport.isEmpty()){
            return false;
        } else {
            ErrorReport existingReport = optionalErrorReport.get();
            existingReport.setDescription(errorReport.getDescription());
            existingReport.setDelay(errorReport.getDelay());
            errorReportRepository.save(existingReport);
        }
       return true;
    }


}
