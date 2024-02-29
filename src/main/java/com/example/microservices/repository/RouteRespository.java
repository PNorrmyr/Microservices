package com.example.microservices.repository;

import com.example.microservices.model.PublicRoute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RouteRespository extends JpaRepository<PublicRoute, Long> {

    public List<PublicRoute> findAllByFavoriteIsTrue();

    public PublicRoute findDistinctByStartLocEqualsIgnoreCaseAndEndLocContainsIgnoreCase(String startLoc, String endLoc);

}
