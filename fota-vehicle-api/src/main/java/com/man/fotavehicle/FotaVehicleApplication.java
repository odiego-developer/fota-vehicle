package com.man.fotavehicle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories( basePackages = {"com.man.fotavehicle.persistence"})
@EntityScan( basePackages = {"com.man.fotavehicle.domain"})
@ComponentScan(
        basePackages = {
            "com.man.fotavehicle.service",
            "com.man.fotavehicle.internal",
            "com.man.fotavehicle.io"
        })
@EnableScheduling()
public class FotaVehicleApplication {
    public static void main(String[] args) {
        SpringApplication.run(FotaVehicleApplication.class);
    }

}
