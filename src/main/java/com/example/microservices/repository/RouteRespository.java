package com.example.microservices.repository;

import com.example.microservices.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRespository extends JpaRepository<Route, Long> {

    public List<Route> findAllByFavoriteIsTrue();
}
