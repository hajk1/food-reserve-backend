package me.hajk1.foodreservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "me.hajk1.foodreservation.repository")
@EntityScan(basePackages = "me.hajk1.foodreservation.model")
@SpringBootApplication
public class FoodReservationApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodReservationApplication.class, args);
    }
}