package com.example.microservices.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "ErrorReport")
public class ErrorReport {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;

    private String description;

    @Column(columnDefinition = "integer default 0")
    private int delay = 0;

}
