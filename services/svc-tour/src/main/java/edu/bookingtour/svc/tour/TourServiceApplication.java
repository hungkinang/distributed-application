package edu.bookingtour.svc.tour;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "edu.bookingtour.svc.tour.domain")
@EnableJpaRepositories(basePackages = "edu.bookingtour.svc.tour.repo")
public class TourServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TourServiceApplication.class, args);
    }
}
