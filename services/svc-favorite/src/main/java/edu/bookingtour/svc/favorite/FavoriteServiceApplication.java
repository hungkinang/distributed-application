package edu.bookingtour.svc.favorite;
import org.springframework.boot.SpringApplication;import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication @EntityScan("edu.bookingtour.svc.favorite.domain") @EnableJpaRepositories("edu.bookingtour.svc.favorite.repo")
public class FavoriteServiceApplication{ public static void main(String[]a){SpringApplication.run(FavoriteServiceApplication.class,a);}}
