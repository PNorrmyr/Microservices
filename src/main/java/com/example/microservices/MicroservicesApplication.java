package com.example.microservices;

import com.example.microservices.model.PublicRoute;
import com.example.microservices.model.Station.Station;
import com.example.microservices.model.Station.StationTypes;
import com.example.microservices.repository.RouteRespository;
import com.example.microservices.repository.StationRepository;
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
        stationRepository.save(new Station(1l, "CentralStation", StationTypes.BUS));
        stationRepository.save(new Station(2l, "Vasastaden", StationTypes.BUS));
        stationRepository.save(new Station(3l, "Odenplan", StationTypes.TRAIN));
        stationRepository.save(new Station(4l, "Rådmansgatan", StationTypes.TRAIN));
        stationRepository.save(new Station(5l, "St Eriksplan", StationTypes.TRAIN));
        stationRepository.save(new Station(6l, "Hötorget", StationTypes.TRAIN));

    }
    public void addRoute(){
        routeRespository.save(new PublicRoute(1l, "Krukmakargatan 1", "Odenplan",
                "16:04", "16:20", 0, 16d, false));
        routeRespository.save(new PublicRoute(2l, "Vasastaden", "centralstation",
                "08:12", "08:46", 1, 34d, true ));
        routeRespository.save(new PublicRoute(3l, "Vasastaden", "odenplan",
                "09:40", "10:05", 3, 25d, false ));
        routeRespository.save(new PublicRoute(4l, "Odenplan", "Hötorget",
                "12:40", "12:43", 0, 3d, false ));
        routeRespository.save(new PublicRoute(5l, "Rådmansgatan", "St Eriksplan",
                "11:20", "11:26", 0, 6d, false ));
    }

    @PostConstruct    public void init(){
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
