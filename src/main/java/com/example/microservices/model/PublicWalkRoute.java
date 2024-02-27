package com.example.microservices.model;

import com.example.microservices.model.DTOs.WalkingRouteDTO;
import com.example.microservices.model.RouteAPI.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicWalkRoute {

    private PublicRoute publicRoute;
    private Route walkingRoute;
}
