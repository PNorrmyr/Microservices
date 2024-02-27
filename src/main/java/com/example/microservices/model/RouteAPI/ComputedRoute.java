package com.example.microservices.model.RouteAPI;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;
import java.util.List;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@Data
public class ComputedRoute {
    Route route;
    LocalTime timeOfArrival;
    Weather weather;
    List<Route> alternativeRoutes;

}
