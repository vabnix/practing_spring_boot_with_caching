package com.vaibhav.caching.controller;

import com.vaibhav.caching.model.Weather;
import com.vaibhav.caching.service.CacheServiceManager;
import com.vaibhav.caching.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/weather")
@Slf4j
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CacheServiceManager cachingService;

    @PostMapping("/")
    public Weather addWeatherInfo(@RequestBody Weather weather){
        return weatherService.addWeatherInfo(weather);
    }

    @GetMapping("/city/{name}")
    public Weather getWeatherByCity(@PathVariable String name){
        return weatherService.getWeatherByName(name);
    }

    @GetMapping("/")
    public List<Weather> getWeather(){
        return weatherService.getWeatherdata();
    }

    @GetMapping("/cache-data")
    public void printCachableContent(){
        cachingService.printCacheContent("city");
    }
}
