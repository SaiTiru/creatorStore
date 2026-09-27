package com.stiru.creatorstore.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                .title("Creator Store API")
                .version("1.0")
                .description("API documentation for the Creator Store application")
                .contact(new Contact()
                        .name("Your Name")
                        .email("your.email@example.com")
                ));
    }
        // Configuration code
}
