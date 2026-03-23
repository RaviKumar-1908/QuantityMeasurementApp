package com.quantitymeasurement.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Automatically redirects anyone visiting http://localhost:8080/ straight to Swagger UI!
        registry.addRedirectViewController("/", "/swagger-ui/index.html");
    }
}
