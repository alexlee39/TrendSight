package com.cisco.TrendSight.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/article/**")
            .allowedOrigins("http://localhost:3000")
//            .allowedOriginPatterns("http://localhost:3000/papers/**")
            .allowedMethods("POST", "GET", "PUT", "DELETE")
            .allowCredentials(true);
    }
}