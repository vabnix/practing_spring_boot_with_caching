package com.vaibhav.caching.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Slf4j
public class CacheServiceManager {

    @Autowired
    private CacheManager manager;

    public void printCacheContent(String cacheName){
        Cache cache = manager.getCache(cacheName);

        if(cache == null){
            log.warn("No Cached record stored!");
        } else{
            log.info("Cache Record for [{}]", cacheName);
            log.info(Objects.requireNonNull(cache.getNativeCache().toString()));
        }

    }
}
