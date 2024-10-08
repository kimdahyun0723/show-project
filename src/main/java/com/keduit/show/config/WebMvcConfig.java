package com.keduit.show.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/profileImages/**")
                .addResourceLocations("file:/C:/Temp/profileImageUpload/");

        registry.addResourceHandler("/boardImages/**")
                .addResourceLocations("file:/C:/Temp/boardImageUpload/");
    }
}
