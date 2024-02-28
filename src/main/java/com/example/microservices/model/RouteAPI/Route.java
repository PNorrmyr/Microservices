package com.example.microservices.model.RouteAPI;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@NoArgsConstructor(access= AccessLevel.PROTECTED, force=true)
@Data
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    @Transient
    final Coordinates startCoords,stopCoords;
    final TypeOfTravel typeOfTravel;
    final Double distance;
    final Double time;
    final String start;
    final String stop;
    @ElementCollection
    List<String> waypoints;



}
