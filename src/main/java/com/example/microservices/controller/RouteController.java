package com.example.microservices.controller;

import com.example.microservices.model.*;
import com.example.microservices.model.DTOs.RouteRequestDTO;
import com.example.microservices.model.Report.Report;
import com.example.microservices.model.Report.Reports;
import com.example.microservices.model.RouteAPI.Coordinates;
import com.example.microservices.model.RouteAPI.Route;
import com.example.microservices.model.Station.Station;
import com.example.microservices.model.Station.StationResponse;
import com.example.microservices.service.RouteService;
import com.example.microservices.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/api/v1/public-transport/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;
    @Autowired
    private StationService stationService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    ReportController reportController;
    @Autowired
    PublicRoute publicRoute;
    @Autowired
    PublicWalkRoute publicWalkRoute;




    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }


    //TODO Work in progress
    @PostMapping
    public ResponseEntity<PublicWalkRoute> getPublicWalkRoute(@RequestBody RouteRequestDTO requestDTO) {
        //Kolla om start eller slut destination inte är en station, om inte, skicka till API och returnera gå tid
        if (!stationService.confirmStation(requestDTO.getStartPos(), requestDTO.getDest())){
            System.out.println("Ingen station, går via API för att hämta tid");

            //Startpos är INTE station, dest är station
            if (stationService.getByName(requestDTO.getStartPos()) == null){
                System.out.println("Start INTE station");
                return ResponseEntity.ok(destIsStation(requestDTO.getStartPos(), requestDTO.getDest()));

            } else if (stationService.getByName(requestDTO.getDest()) == null) {
                //Startpos är station, dest är INTE station
                System.out.println("dest INTE station");
                return ResponseEntity.ok(startIsStation(requestDTO.getStartPos(), requestDTO.getDest()));
            } else {
            //Kollar om rutt har förseningar och tar fram längsta möjliga försening på linjen
            publicRoute = routeService.getPublicRoute(requestDTO.getStartPos(), requestDTO.getDest());

            //Hittar delayedTravelTime om reports finns tillgänglig
            double maxDelay = maxDelay(publicRoute);
            double delayedTravelTime = delayedTravelTime(maxDelay, publicRoute);

            //Om delayedTravelTime är längre än tiden att gå, returnera en gå rutt
            publicWalkRoute = delayedWalkRoute(delayedTravelTime, publicRoute, publicWalkRoute, requestDTO.getStartPos(), requestDTO.getDest());
            return ResponseEntity.status(200).body(publicWalkRoute);
            }
        }
        //Sätter kommunal rutt om bägge destinationer är Stationer
        System.out.println("Hämtar kommunal rutt");
        publicWalkRoute.setPublicRoute(publicRoute);
        return ResponseEntity.status(200).body(publicWalkRoute);
    }

    @PostMapping("/stations")
    public ResponseEntity<StationResponse> getClosestStation(@RequestBody Coordinates coordinates){
        Map<Double, Station> coordinateDist = new HashMap<>();

        for (Station station : stationService.findAll()) {
            coordinateDist.put(coordinates.getDistance(station.getCoords()), station);
        }
        double minDistance = Collections.min(coordinateDist.keySet());
        StationResponse stationResponse = new StationResponse();
        stationResponse.setStation(coordinateDist.get(minDistance));
        stationResponse.setDist(minDistance);


        return ResponseEntity.ok(stationResponse);
    }

    @GetMapping("/delay/{id}")
    public ResponseEntity<Integer> getDelay(@PathVariable int id){
        Reports reports = reportController.getReports().getBody();
        assert reports != null;
        try {
            Report report = reports.getReports().get(id - 1);
            return ResponseEntity.status(200).body(report.getDelay());
        } catch (IndexOutOfBoundsException e){
            return ResponseEntity.status(404).build();
        }
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
                routeService.updateFavorite(exisingRoute);
                return ResponseEntity.status(200).body("Route was unmarked from favorites");
            } else {
                exisingRoute.setFavorite(true);
                routeService.updateFavorite(exisingRoute);
                return ResponseEntity.status(200).body("Route was marked as favorite");
            }
        }
    }

    public PublicWalkRoute destIsStation(String start, String dest){
        Route route = routeService.getWalkingRoute(start, dest);
        Coordinates startCoord = route.getStartCoords();

        Map<Double, Station> coordinateDist = new HashMap<>();
        for (Station station : stationService.findAll()) {
            coordinateDist.put(startCoord.getDistance(station.getCoords()), station);
        }
        double minDistance = Collections.min(coordinateDist.keySet());

        Station station = coordinateDist.get(minDistance);
        Route walkingroute = routeService.getWalkingRoute(start, station.getCoords().asString());

        publicRoute = routeService.getPublicRoute(station.getName(), dest);

        publicWalkRoute.setWalkingRoute(walkingroute);
        publicWalkRoute.setPublicRoute(publicRoute);
        return publicWalkRoute;
    }

    public PublicWalkRoute startIsStation(String start, String dest){
        Route route = routeService.getWalkingRoute(start, dest);
        Coordinates destCoord = route.getStopCoords();
        Map<Double, Station> coordinateDist = new HashMap<>();
        for (Station station : stationService.findAll()) {
            coordinateDist.put(destCoord.getDistance(station.getCoords()), station);
        }
        double minDistance = Collections.min(coordinateDist.keySet());

        Station station = coordinateDist.get(minDistance);
        Route walkingRoute = routeService.getWalkingRoute(station.getCoords().asString(), dest);

        publicRoute = routeService.getPublicRoute(start, station.getName());

        publicWalkRoute.setPublicRoute(publicRoute);
        publicWalkRoute.setWalkingRoute(walkingRoute);
        return publicWalkRoute;
    }

    public double maxDelay(PublicRoute publicRoute) {
        ArrayList<Integer> delays = new ArrayList<>();
        double maxDelay = 0;


        if (!publicRoute.getReports().isEmpty()) {
            for (Report r : publicRoute.getReports()) {
                delays.add(r.getDelay());
            }
            maxDelay = Collections.max(delays);
        }
        return maxDelay;
    }

    public double delayedTravelTime(double maxDelay, PublicRoute publicRoute){
        return maxDelay + publicRoute.getTravelTime();
    }

    public PublicWalkRoute delayedWalkRoute(double delayedTravelTime, PublicRoute publicRoute, PublicWalkRoute publicWalkRoute, String start, String dest){
        if (delayedTravelTime > publicRoute.getTravelTime()) {
            System.out.println("Hämtar gå rutt delay är större än gå-tid");
            Route walkingRoute = routeService.getWalkingRoute(start, dest);
            publicWalkRoute.setWalkingRoute(walkingRoute);
        }
        return publicWalkRoute;
    }

}
