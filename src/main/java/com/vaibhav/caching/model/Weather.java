package com.vaibhav.caching.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "weather")
public class Weather {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String forecast;
    @Version
    private Long version;
}
