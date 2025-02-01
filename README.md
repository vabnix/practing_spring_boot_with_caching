# Spring Boot with Caching

### Reference Documentation
Spring Boot application demonstrating caching functionality.

Caching is crucial for improving application performance by storing frequently accessed data in memory, reducing database calls and computational overhead.

### Dependencies Setup:

- spring-boot-starter-cache: Provides Spring's caching abstraction
- caffeine: High-performance caching library (alternative to EhCache)
- lombok: Reduces boilerplate code
- spring-boot-starter-test: For testing cache behavior

### Some more details:
* @Cacheable: Stores method results in cache
* @CacheEvict: Removes data from cache
* @CachePut: Updates cache data
* @EnableCaching: Enables caching in Spring Boot application

### How to Use
* Run the application
* First call to /api/products/{id} will be slow (3 seconds)
* Subsequent calls to same ID will be instant (cached)
* Call POST /api/products/{id}/update to evict cache
* Next GET call will be slow again as cache was cleared
