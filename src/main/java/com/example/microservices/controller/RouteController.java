package com.example.microservices.controller;

import com.example.microservices.model.*;
import com.example.microservices.model.DTOs.RouteRequestDTO;
import com.example.microservices.model.Report.Report;
import com.example.microservices.model.Report.Reports;
import com.example.microservices.model.RouteAPI.Route;
import com.example.microservices.service.RouteService;
import com.example.microservices.service.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
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
    @Autowired
    ReportController reportController;




    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }


    //TODO Work in progress
    @PostMapping
    public ResponseEntity<PublicWalkRoute> getPublicRoute(@RequestBody RouteRequestDTO requestDTO) {

        PublicRoute publicRoute = new PublicRoute();
        PublicWalkRoute publicWalkRoute = new PublicWalkRoute();
        //Kolla om start eller slut destination inte är en station, om inte, skicka till API och returnera gå tid
        System.out.println(stationService.confirmStation(requestDTO.getStartPos(), requestDTO.getDest()));
        if (!stationService.confirmStation(requestDTO.getStartPos(), requestDTO.getDest())){
            System.out.println("Ingen station, går via API för att hämta tid");
            Route walkingRoute = routeService.getWalkingRoute(requestDTO.getStartPos(), requestDTO.getDest());
            publicRoute.setTravelTime(walkingRoute.getTime());
            publicRoute.setId(walkingRoute.getId());
            publicWalkRoute.setPublicRoute(publicRoute);


            return ResponseEntity.status(200).body(publicWalkRoute);
        } else {
            //Få fram den längsta möjliga försening på linjen.
            publicRoute = routeService.getPublicRoute(requestDTO.getStartPos(), requestDTO.getDest());
            ArrayList<Integer> delays = new ArrayList<>();

            //Hittar max delay om reports finns tillgänglig
            double maxDelay;
            double delayedTravelTime;

            if (!publicRoute.getReports().isEmpty()) {
                for (Report r : publicRoute.getReports()) {
                    delays.add(r.getDelay());
                }
                maxDelay = Collections.max(delays);
                delayedTravelTime = maxDelay + publicRoute.getTravelTime();

                //Om total restid med delay är längre än tiden att gå, returnera en gå rutt
                if (delayedTravelTime > publicRoute.getTravelTime()) {
                    System.out.println("Hämtar gå rutt delay är större än gå-tid");
                    Route walkingRoute = routeService.getWalkingRoute(requestDTO.getStartPos(), requestDTO.getDest());
                    publicWalkRoute.setWalkingRoute(walkingRoute);
                }
                return ResponseEntity.status(200).body(publicWalkRoute);
            }

            //Sätter kommunal rutt om bägge destinationer är Stationer och
            System.out.println("Hämtar kommunal rutt");
            publicWalkRoute.setPublicRoute(publicRoute);

            return ResponseEntity.status(200).body(publicWalkRoute);
        }

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
}
