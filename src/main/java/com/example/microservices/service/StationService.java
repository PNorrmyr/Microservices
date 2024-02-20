package com.example.microservices.service;

import com.example.microservices.model.Route;
import com.example.microservices.model.Station;
import com.example.microservices.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    RestTemplate restTemplate;

    public Station getByName(String name){
        return stationRepository.findStationByNameEqualsIgnoreCase(name);
    }

    public void getRoute(String startLoc, String endLoc){

        if (getByName(startLoc) == null || getByName(endLoc) == null) {

            //Om den inte hittar en station, skicka api till enskild transport och hämta gå tid
            ResponseEntity<Route> walkingRoute = restTemplate
                    .getForEntity("", walkingRoute.class);



        }

    }


}
