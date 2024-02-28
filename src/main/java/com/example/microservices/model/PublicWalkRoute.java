package com.example.microservices.model;

import com.example.microservices.model.RouteAPI.Route;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class PublicWalkRoute {

    private PublicRoute publicRoute;
    private Route walkingRoute;
}
