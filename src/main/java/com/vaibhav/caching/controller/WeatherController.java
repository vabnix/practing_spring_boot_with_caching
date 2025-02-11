package com.vaibhav.caching.controller;

import com.vaibhav.caching.model.Weather;
import com.vaibhav.caching.service.CacheServiceManager;
import com.vaibhav.caching.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RestController
@RequestMapping("/api/weather")
@Slf4j
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private CacheServiceManager cachingService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

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

    @GetMapping("/redis-data")
    public Map<String, Object> getRedisData() {
        Map<String, Object> data = new HashMap<>();
        Set<String> keys = redisTemplate.keys("*");
        if (keys != null) {
            for (String key : keys) {
                data.put(key, redisTemplate.opsForValue().get(key));
            }
        }
        return data;
    }
}
