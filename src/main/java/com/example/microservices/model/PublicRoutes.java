package com.example.microservices.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PublicRoutes {
    private List<PublicRoute> publicRoutes;
}
