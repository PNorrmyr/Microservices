package com.example.microservices.controller;

import com.example.microservices.model.PublicRoute;
import com.example.microservices.model.PublicRoutes;
import com.example.microservices.model.RouteRequestDTO;
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
    public ResponseEntity<PublicRoutes> getPublicRoute(@RequestBody RouteRequestDTO requestDTO) {

        //Kolla om start eller slut destination inte är en station
        if (!stationService.confirmStation(requestDTO.getStartPos(), requestDTO.getDest())){
            //Om den inte hittar en station, skicka api till enskild transport och hämta gå tid
            System.out.println(routeService.getWalkingRoute(requestDTO.getStartPos(), requestDTO.getDest()));

        }

        //Annars ge rutt
            PublicRoutes publicRoutes = new PublicRoutes();
            publicRoutes.setPublicRoutes(routeService.getPublicRoute(requestDTO.getStartPos(), requestDTO.getDest()));
            return ResponseEntity.status(200).body(publicRoutes);

    }

    @GetMapping("/favorite")
    public ResponseEntity<List<PublicRoute>> getFavorite(@RequestHeader("username") String username){
        List<PublicRoute> favoriteList = routeService.getByFavorite(username);
        if (favoriteList.isEmpty()){
            return ResponseEntity.status(204).build();
        } else {
            return ResponseEntity.status(200).body(favoriteList);
        }
    }

    @PatchMapping("/favorite/{id}")
    public ResponseEntity<String> toggleFavorite(@RequestHeader("username") String username, @PathVariable Long id){
        Optional<PublicRoute> optionalRoute = routeService.getById(id);
        if (optionalRoute.isEmpty()){
            return ResponseEntity.status(404).body("Could not find route with id " + id);
        } else {
            PublicRoute exisingRoute = optionalRoute.get();
            if (exisingRoute.getFavorite()){
                exisingRoute.setFavorite(false);
                routeService.updateFavorit(exisingRoute);
                return ResponseEntity.status(200).body("Route was unmarked from favorites");
            } else {
                exisingRoute.setFavorite(true);
                routeService.updateFavorit(exisingRoute);
                return ResponseEntity.status(200).body("Route was marked as favorite");
            }
        }
    }
}
