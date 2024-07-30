package com.vasylenko.application.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration.
 * <p> Configures the application with the OpenAPI bean for API documentation. </p>
 */
@Configuration
public class SwaggerConfiguration {

    /**
     * Configures the OpenAPI bean with custom API documentation details.
     *
     * @return the configured OpenAPI instance
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Documentation")
                        .version("1.0")
                        .description("API documentation for the application"));
    }
}