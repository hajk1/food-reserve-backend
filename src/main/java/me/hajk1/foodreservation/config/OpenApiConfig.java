package me.hajk1.foodreservation.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
    name = "bearerAuth",
    description = "JWT auth description",
    scheme = "bearer",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
  @Value("${api.base-url}") // Inject the property from application.properties
  private String apiBaseUrl;

    @Bean
    public OpenAPI openAPI() {
    return new OpenAPI()
        .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
        .components(new Components())
        .info(
            new Info()
                .title("Food Reservation API")
                .version("1.0")
                .description("API for Food Reservation System")
                .license(new License().name("Apache 2.0")))
        .addServersItem(
            new io.swagger.v3.oas.models.servers.Server()
                .url(apiBaseUrl)); // Dynamically set the server URL
    }
}