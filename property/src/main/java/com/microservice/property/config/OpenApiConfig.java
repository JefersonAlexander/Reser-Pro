package com.microservice.property.config;

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
                        .title("API de Microservicio de Propiedades")
                        .version("1.0.0")
                        .description("Esta API REST gestiona las Propiedades, Usuarios, Amenities y Publicaciones del sistema.")
                        .termsOfService("http://swagger.io/terms/") // URL de tus términos de servicio
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }
}