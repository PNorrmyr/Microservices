package com.example.microservices.service;

import com.example.microservices.model.Route;
import com.example.microservices.repository.RouteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class RouteService {
    @Autowired
    private RouteRespository routeRespository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    public RouteService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

   /* public String callTrip(String url, String apiKey){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorisation", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class );
        return response.getBody();
    }*/

    public List<Route> getByFavorite(@RequestHeader("usename") String username){
        return routeRespository.findAllByFavoriteIsTrue();
    }

    public Optional<Route> getById(Long Id){
        return routeRespository.findById(Id);
    }

    public void updateFavorit(Route route){
        routeRespository.save(route);
    }







}
