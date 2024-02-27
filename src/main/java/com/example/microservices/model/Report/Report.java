package com.example.microservices.model.Report;

import com.example.microservices.model.PublicRoute;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table (name = "Report")
@ToString
public class Report {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String description;

    @Column(columnDefinition = "integer default 0")
    private Integer delay = 0;

    @ToString.Exclude
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "routeId")
    private PublicRoute route;


}
