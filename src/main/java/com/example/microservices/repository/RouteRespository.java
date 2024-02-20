package com.example.microservices.repository;

import com.example.microservices.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRespository extends JpaRepository<Route, Long> {


}
