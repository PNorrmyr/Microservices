package com.example.microservices.controller;

import com.example.microservices.model.ResponseDTO;
import com.example.microservices.model.Route;
import com.example.microservices.model.RouteRequestDTO;
import com.example.microservices.model.WalkingRouteDTO;
import com.example.microservices.service.RouteService;
import com.example.microservices.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/public-transport/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;
    @Autowired
    private StationService stationService;
    @Autowired
    RestTemplate restTemplate;



    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }


    //TODO Work in progress
    @PostMapping
    public ResponseDTO getRoute(@RequestBody RouteRequestDTO routeRequestDTO) {

        //Kollar om start eller slut destination inte är en station
        if (!stationService.confirmStation(routeRequestDTO.getStartPos(), routeRequestDTO.getDest())) {
            System.out.println(routeRequestDTO.getStartPos());
            //Om den inte hittar en station, skicka api till enskild transport och hämta gå tid
            return routeService.getRoute(routeRequestDTO.getStartPos(), routeRequestDTO.getDest());
            //Annars ge rutt

        }
        return null;
    }

    @GetMapping("/favorite")
    public List<Route> getFavorite(@RequestHeader("username") String username){
        return routeService.getByFavorite(username);
    }

    @PatchMapping("/favorite/{id}")
    public ResponseEntity<String> toggleFavorite(@RequestHeader("username") String username, @PathVariable Long id){
        Optional<Route> optionalRoute = routeService.getById(id);
        if (optionalRoute.isEmpty()){
            return ResponseEntity.status(404).body("Could not find route with id " + id);
        } else {
            Route exisingRoute = optionalRoute.get();
            if (exisingRoute.getFavorite()){
                exisingRoute.setFavorite(false);
                routeService.updateFavorit(exisingRoute);
                return ResponseEntity.status(201).body("Route was unmarked from favorites");
            } else {
                exisingRoute.setFavorite(true);
                routeService.updateFavorit(exisingRoute);
                return ResponseEntity.status(201).body("Route was marked as favorite");
            }
        }
    }
}
