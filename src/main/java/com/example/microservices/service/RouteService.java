package com.example.microservices.service;

import com.example.microservices.repository.RouteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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

    public String callTrip(String url, String apiKey){
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorisation", "Bearer " + apiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class );
        return response.getBody();
    }






}
