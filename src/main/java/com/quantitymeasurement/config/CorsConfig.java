package com.quantitymeasurement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                .allowedOrigins(
                	    "http://localhost:5174",
                	    "http://quantity-app-frontend-ravi.s3-website-us-east-1.amazonaws.com"
                	)                    
                .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowCredentials(true); // 🔥 VERY IMPORTANT
            }
        };
    }
}