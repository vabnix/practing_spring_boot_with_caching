package com.vaibhav.caching.service;

import com.vaibhav.caching.model.Weather;
import com.vaibhav.caching.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WeatherService {

    @Autowired
    private WeatherRepository weatherRepository;


    public Weather addWeatherInfo(Weather weather) {
        try {
            return weatherRepository.save(weather);
        } catch (ObjectOptimisticLockingFailureException e){
            Weather refreshedData = weatherRepository.findById(weather.getId())
                    .orElseThrow(() -> new RuntimeException("Weather not found"));

            // Update the refreshed data with new values
            refreshedData.setName(weather.getName());
            refreshedData.setForecast(weather.getForecast());

            log.info("Retrying save after optimistic lock exception for weather id: {}", weather.getId());
            return weatherRepository.save(refreshedData);
        }
    }

    @Cacheable("city")
    public Weather getWeatherByName(String name) {
        log.info("Fetching weather data for city: {}", name);
        return weatherRepository.findByName(name);
    }

    public List<Weather> getWeatherdata() {
        return weatherRepository.findAll();
    }
}
