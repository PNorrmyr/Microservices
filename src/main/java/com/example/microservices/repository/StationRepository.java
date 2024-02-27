package com.example.microservices.repository;

import com.example.microservices.model.Station.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StationRepository extends JpaRepository<Station, Long> {

    public Station findDistinctFirstByNameEqualsIgnoreCase(String name);

    public List<Station> findAll();

}
