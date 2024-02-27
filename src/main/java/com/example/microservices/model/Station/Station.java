package com.example.microservices.model.Station;

import com.example.microservices.model.RouteAPI.Coordinates;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Stations")
public class Station {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String name;

    @Embedded
    private Coordinates Coords;

    @Enumerated(EnumType.STRING)
    private StationTypes type;
}
