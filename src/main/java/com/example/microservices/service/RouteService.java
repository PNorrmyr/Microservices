package com.example.microservices.service;

import com.example.microservices.model.PublicRoute;
import com.example.microservices.model.PublicRoutes;
import com.example.microservices.model.WalkingRouteDTO;
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

    public void updateFavorit(PublicRoute route){
        routeRespository.save(route);
    }



    //TODO Work in progress getting error
    public WalkingRouteDTO getWalkingRoute(String startLoc, String dest){
        ResponseEntity<WalkingRouteDTO> responseEntity = restTemplate
                .getForEntity("http://localhost:8081/api/v1/routes/foot/" + startLoc + "/" + dest, WalkingRouteDTO.class);

        WalkingRouteDTO walkingRoutesDTO = new WalkingRouteDTO();
        System.out.println(responseEntity.getBody().getTime());
        walkingRoutesDTO.setTime(responseEntity.getBody().getTime());
        walkingRoutesDTO.setDistance(responseEntity.getBody().getDistance());
        return walkingRoutesDTO;
    }


    public List<PublicRoute> getPublicRoute(String startPos, String dest){
        return routeRespository.findAllByStartLocEqualsIgnoreCaseAndEndLocContainsIgnoreCase(startPos, dest);

    }
}





