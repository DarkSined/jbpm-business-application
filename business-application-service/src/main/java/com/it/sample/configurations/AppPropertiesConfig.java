package com.it.sample.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
@ConfigurationProperties(prefix = "app-config")
public class AppPropertiesConfig {
    
    private String deploymendId;
}
