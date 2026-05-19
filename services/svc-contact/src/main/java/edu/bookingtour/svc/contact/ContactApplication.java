package edu.bookingtour.svc.contact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("edu.bookingtour.svc.contact.domain")
@EnableJpaRepositories("edu.bookingtour.svc.contact.repo")
public class ContactApplication {
    public static void main(String[] args) {
        SpringApplication.run(ContactApplication.class, args);
    }
}
