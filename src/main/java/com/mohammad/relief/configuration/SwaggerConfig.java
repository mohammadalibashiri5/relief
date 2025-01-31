package com.mohammad.relief.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(new Info().title("Relief API"))
                .addSecurityItem(new SecurityRequirement().addList("Use Log in to generate token"))
                .components(new Components().addSecuritySchemes("Use Log in to generate token", new SecurityScheme()
                        .name("Use Log in to generate token").type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));

    }
}