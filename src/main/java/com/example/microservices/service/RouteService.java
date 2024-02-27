package com.example.microservices.service;

import com.example.microservices.model.PublicRoute;
import com.example.microservices.model.RouteAPI.ComputedRoute;
import com.example.microservices.model.RouteAPI.Route;
import com.example.microservices.repository.RouteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    @Autowired
    private RouteRespository routeRespository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public RouteService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public List<PublicRoute> getByFavorite(@RequestHeader("username") String username){
        return routeRespository.findAllByFavoriteIsTrue();
    }

    public Optional<PublicRoute> getById(Long Id){
        return routeRespository.findById(Id);
    }

    public void updateFavorite(PublicRoute route){
        routeRespository.save(route);
    }



    //TODO Work in progress getting error. Need to change so that walking time is fetched from api
    public Route getWalkingRoute(String startLoc, String dest){
        ResponseEntity<ComputedRoute> responseEntity = restTemplate
                .getForEntity("https://tohemu23.azurewebsites.net/api/v1/routes/Foot/" + startLoc + "/" + dest, ComputedRoute.class);

        return responseEntity.getBody().getRoute();
    }


    public PublicRoute getPublicRoute(String startPos, String dest){
        return routeRespository.findDistinctByStartLocEqualsIgnoreCaseAndEndLocContainsIgnoreCase(startPos, dest);

    }
}





