package com.example.microservices.model.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReportDTO {
    private String description;
    private Integer delay;
    private Long routeId;
}
