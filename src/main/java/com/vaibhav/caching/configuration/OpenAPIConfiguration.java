package com.vaibhav.caching.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.info.Contact;

@Configuration
public class OpenAPIConfiguration {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot API - Using Caching")
                        .version("1.0")
                        .description("Documentation for Product API")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Vaibhav Srivastava")
                                .url("http://ish-aum.com")
                                .email("info@ish-aum.com")));
    }
}
