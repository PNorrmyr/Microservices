package com.example.microservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class WalkingRouteDTO {
    private Long distance;
    private LocalTime timeOfArrival;} //Jag ska få tillbaks gå-tiden från api
