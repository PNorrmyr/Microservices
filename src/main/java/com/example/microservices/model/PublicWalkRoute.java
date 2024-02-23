package com.example.microservices.model;

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
