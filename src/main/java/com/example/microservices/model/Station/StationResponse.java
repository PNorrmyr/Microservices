package com.example.microservices.model.Station;

import com.example.microservices.model.RouteAPI.Coordinates;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationResponse {

    private Station station;

    private double dist;

}
