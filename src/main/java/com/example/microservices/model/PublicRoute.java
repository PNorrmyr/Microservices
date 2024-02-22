package com.example.microservices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String travelTime;

    private Boolean favorite;

}
