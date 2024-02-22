package com.example.microservices.service;

import com.example.microservices.model.ResponseDTO;
import com.example.microservices.model.Route;
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

    public List<Route> getByFavorite(@RequestHeader("username") String username){
        return routeRespository.findAllByFavoriteIsTrue();
    }

    public Optional<Route> getById(Long Id){
        return routeRespository.findById(Id);
    }

    public void updateFavorit(Route route){
        routeRespository.save(route);
    }



    //TODO Work in progress getting error
    public ResponseDTO getRoute(String startLoc, String dest){
        ResponseDTO responseDTO = new ResponseDTO();

        ResponseEntity<WalkingRouteDTO> responseEntity = restTemplate
                .getForEntity("https://localhost:8081/api/v1/routes/Foot/" + startLoc + "/" + dest, WalkingRouteDTO.class);

        WalkingRouteDTO walkingRouteDTO = responseEntity.getBody();

        responseDTO.setWalkingRouteDTO(walkingRouteDTO);
        return responseDTO;
    }
}





