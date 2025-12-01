package com.microservice.booking.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Reservation Microservice")
                        .version("1.0.0")
                        .description("This REST API manages reservations, clients, and reservation statuses in the system.")
                        .termsOfService("http://swagger.io/terms/") // URL de tus términos de servicio
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}