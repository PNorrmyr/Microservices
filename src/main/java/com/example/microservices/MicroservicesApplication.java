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
import com.example.microservices.model.RouteAPI.Coordinates;

@SpringBootApplication
public class MicroservicesApplication {

    @Autowired
    StationRepository stationRepository;
    @Autowired
    RouteRespository routeRespository;


    public void addStation(){
        stationRepository.save(new Station(1l, "Centralstation", new Coordinates(59.33015d, 18.05821d), StationTypes.BUS));
        stationRepository.save(new Station(2l, "Hagalund", new Coordinates(59.363754d, 18.008201d), StationTypes.BUS));
        stationRepository.save(new Station(3l, "Odenplan", new Coordinates(59.34300820438362d, 18.04978350642927d), StationTypes.TRAIN));
        stationRepository.save(new Station(4l, "Rådmansgatan",new Coordinates(59.3402737d, 18.0590682d), StationTypes.TRAIN));
        stationRepository.save(new Station(5l, "St Eriksplan", new Coordinates(59.3394381d, 18.0369903d), StationTypes.TRAIN));
        stationRepository.save(new Station(6l, "Hötorget", new Coordinates(59.3358309d, 18.0632652d), StationTypes.TRAIN));
        stationRepository.save(new Station(7l, "Ropsten", new Coordinates(59.3573561d, 18.1023962d), StationTypes.TRAIN));
        stationRepository.save(new Station(8l, "Råsunda", new Coordinates(59.365427d, 59.365427d), StationTypes.BUS));
        stationRepository.save(new Station(9l, "Näckrosen", new Coordinates(59.366444d, 17.983181d), StationTypes.TRAIN));


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
        routeRespository.save(new PublicRoute(5l, "Rådmansgatan", "Ropsten",
                "11:20", "11:26", 0, 6d, false ));
        routeRespository.save(new PublicRoute(5l, "Hötorget", "Ropsten",
                "11:20", "11:26", 0, 6d, false ));
        routeRespository.save(new PublicRoute(5l, "Ropsten", "Hötorget",
                "11:20", "11:26", 0, 6d, false ));


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
