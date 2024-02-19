package com.example.microservices;

import com.example.microservices.model.Station;
import com.example.microservices.model.StationTypes;
import com.example.microservices.repository.StationRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MicroservicesApplication {

    @Autowired
    StationRepository stationRepository;


    public void addStation(){
        stationRepository.save(new Station(1l,"Krukmakargatan 1", StationTypes.BUS));
    }


    @PostConstruct
    public void init(){
        addStation();
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroservicesApplication.class, args);
    }


}
