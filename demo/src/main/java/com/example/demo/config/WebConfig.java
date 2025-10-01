package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // Перенаправление с корневого URL на index.html
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.addViewController("/alerts").setViewName("forward:/index.html");
        registry.addViewController("/alerts/**").setViewName("forward:/index.html");
    }
}