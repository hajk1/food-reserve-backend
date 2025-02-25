package me.hajk1.foodreservation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Value("${cors.enabled:true}")
  private boolean corsEnabled;

  // @Override
  public void addCorsCrossOrigins(CorsRegistry registry) {
    registry
        .addMapping("/api/**")
        .allowedMethods("GET", "POST", "PUT", "DELETE")
        .allowedHeaders("*")
        .allowedOrigins("*");
  }

  @Override
  public void addCorsMappings(CorsRegistry registry) {
    if (corsEnabled) {
      registry
          .addMapping("/api/**")
          .allowedOrigins(
              "http://localhost:3000",
              "https://a-mohamadi.ir") // Allow localhost and https://a-mohamadi.ir
          .allowedMethods("GET", "POST", "PUT", "DELETE")
          .allowedHeaders("*");
    }
  }
}
