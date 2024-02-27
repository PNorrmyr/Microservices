package com.example.microservices.model.RouteAPI;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Tobias Heidlund
 */
@AllArgsConstructor
@Data
public class Weather {
    int temprature;
    String type;
}
