package com.example.microservices.model;

import com.example.microservices.model.DTOs.WalkingRouteDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicWalkRoute {

    private PublicRoute publicRoute;
    private WalkingRouteDTO walkingRouteDTO;
}
