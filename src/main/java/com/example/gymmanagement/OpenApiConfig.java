package com.example.gymmanagement;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Gym Management API",
                description = "REST API for Gym Management System",
                version = "1.0"))
public class OpenApiConfig {
}
