package edu.bookingtour.svc.favorite.config;
import lombok.Data;import org.springframework.boot.context.properties.ConfigurationProperties;
@Data @ConfigurationProperties(prefix="jwt") public class JwtProperties { private String secret;}
