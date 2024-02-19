package com.example.microservices.service;

import com.example.microservices.repository.RouteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RouteService {
    @Autowired
    private RouteRespository routeRespository;



}
