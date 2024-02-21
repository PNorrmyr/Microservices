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

    public Station getByName(String name){
        return stationRepository.findStationByNameEqualsIgnoreCase(name);
    }

    public boolean confirmStation(String startLoc, String endLoc){

        if (getByName(startLoc) == null || getByName(endLoc) == null) {
            return false;
        }
        return true;
    }


}
