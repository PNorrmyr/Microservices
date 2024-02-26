package com.example.microservices.model.Report;

import com.example.microservices.model.PublicRoute;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "Report")
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String description;

    @Column(columnDefinition = "integer default 0")
    private Integer delay = 0;

    @ManyToOne
    @JoinColumn(name = "routeId")
    private PublicRoute route;


}
