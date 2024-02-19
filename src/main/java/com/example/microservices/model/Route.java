package com.example.microservices.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Routes")
public class Route {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String departTime;

    private String arrival;

    private String swap;

    private String travelTime;

}
