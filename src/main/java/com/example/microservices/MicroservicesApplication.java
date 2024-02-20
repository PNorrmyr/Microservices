package com.example.microservices;

import com.example.microservices.model.Route;
import com.example.microservices.model.Station;
import com.example.microservices.model.StationTypes;
import com.example.microservices.repository.RouteRespository;
import com.example.microservices.repository.StationRepository;
import com.example.microservices.service.RouteService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class MicroservicesApplication {

    @Autowired
    StationRepository stationRepository;
    @Autowired
    RouteRespository routeRespository;


    public void addStation(){
        stationRepository.save(new Station(1l,"Krukmakargatan 1", StationTypes.BUS));
        stationRepository.save(new Station(2l, "CentralStation", StationTypes.BUS));
        stationRepository.save(new Station(3l, "Centralstation", StationTypes.TRAIN));
        stationRepository.save(new Station(4l, "Vasastaden", StationTypes.BUS));
        stationRepository.save(new Station(5l, "Odenplan", StationTypes.TRAIN));
        stationRepository.save(new Station(6l, "Odenplan", StationTypes.BUS));
    }
    public void addRoute(){
        routeRespository.save(new Route(1l, "Krukmakargatan 1", "Odenplan", "16:04", "16:20", 0, "16", false ));
        routeRespository.save(new Route(2l, "Vasastaden", "centralstation","08:12", "08:46", 1, "34", true ));
    }

    @PostConstruct
    public void init(){
        addStation();
        addRoute();
    }

    public static void main(String[] args) {
        SpringApplication.run(MicroservicesApplication.class, args);
    }

    @Bean
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }


}
