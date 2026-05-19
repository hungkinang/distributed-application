package edu.bookingtour.svc.review;
import org.springframework.boot.SpringApplication;import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan; import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
@SpringBootApplication @EntityScan("edu.bookingtour.svc.review.domain") @EnableJpaRepositories("edu.bookingtour.svc.review.repo") public class ReviewServiceApplication{
 public static void main(String[] args){SpringApplication.run(ReviewServiceApplication.class,args);}
}
