package com.vaibhav.caching.repository;

import com.vaibhav.caching.model.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherRepository extends JpaRepository<Weather, Integer> {

    Weather findByName(String name);
}
