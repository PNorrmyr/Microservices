package com.example.microservices.service;

import com.example.microservices.model.Station.Station;
import com.example.microservices.repository.StationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    public Station getByName(String name){
        return stationRepository.findDistinctFirstByNameEqualsIgnoreCase(name);
    }

    public boolean confirmStation(String startLoc, String endLoc){

        if (getByName(startLoc) == null || getByName(endLoc) == null) {
            return true;
        }
        return false;
    }


}
