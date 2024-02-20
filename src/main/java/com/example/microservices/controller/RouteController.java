package com.example.microservices.controller;

import com.example.microservices.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/public-transport/routes")
public class RouteController {

    @Autowired
    private RouteService routeService;


    public RouteController(RouteService routeService){
        this.routeService = routeService;
    }

    @GetMapping
    public String getTrip(){
        String apiKey = "81137d7358a64f9eb6bcddbb99170f21";
        String url = "https://journeyplanner.integration.sl.se/v1/TravelplannerV3_1/xsd.xml?key=" + apiKey;
        return routeService.callTrip(url, apiKey);
    }

}
