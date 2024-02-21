package com.example.microservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Data

//TODO test class
public class WalkingRouteDTO {
        long id;
        TypeOfTravel typeOfTravel;
        Long distance;
        String time;
        String start;
        String stop;
        String waypoints;
}
