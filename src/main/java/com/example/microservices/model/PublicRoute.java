package com.example.microservices.model;

import com.example.microservices.model.Report.Report;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Route")
public class PublicRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String startLoc;

    private String endLoc;

    private String departTime;

    private String arrival;

    private int numberOfSwaps;

    private double travelTime;

    private Boolean favorite;

    @OneToMany(mappedBy = "route")
    private List<Report> reports;

    public PublicRoute(Long id, String startLoc, String endLoc, String departTime, String arrival, int numberOfSwaps, double travelTime, Boolean favorite) {
        this.Id = id;
        this.startLoc = startLoc;
        this.endLoc = endLoc;
        this.departTime = departTime;
        this.arrival = arrival;
        this.numberOfSwaps = numberOfSwaps;
        this.travelTime = travelTime;
        this.favorite = favorite;
        // Initialize reports to an empty list or based on some logic
        this.reports = new ArrayList<>();
    }

}
