package com.ajay.microservices.inventory.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI inventoryServiceAPI() {
        return new OpenAPI()
                .info(new Info().title("Inventory Service API")
                        .description("Inventory Service API for managing products")
                        .version("v0.0.1")
                        .license(new License().name("Apache 2.0"))
                        .contact(new Contact().name("Ajay Dhami")))
                .externalDocs(new ExternalDocumentation()
                        .description("You can refer to the Inventory Service Documentation")
                        .url("http://localhost:8082/swagger-ui.html"));

    }
}