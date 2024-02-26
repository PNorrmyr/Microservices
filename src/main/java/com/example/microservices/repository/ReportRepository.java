package com.example.microservices.repository;

import com.example.microservices.model.Report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
