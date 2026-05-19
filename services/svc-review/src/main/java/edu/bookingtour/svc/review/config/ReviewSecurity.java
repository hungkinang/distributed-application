package edu.bookingtour.svc.review.config;
import edu.bookingtour.commons.jwt.JwtSupport;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
@Configuration @EnableWebSecurity @EnableConfigurationProperties(JwtProperties.class)
public class ReviewSecurity {
  @Bean JwtDecoder dec(JwtProperties p){return JwtSupport.resourceServerJwtDecoder(p.getSecret());}
  @Bean JwtAuthenticationConverter cc(){return JwtSupport.jwtAuthenticationConverter();}
  @Bean SecurityFilterChain ch(HttpSecurity http, JwtDecoder d, JwtAuthenticationConverter c) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable).sessionManagement(s->s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
      .authorizeHttpRequests(a->a.requestMatchers("/api/reviews/info","/api/reviews/tour/**").permitAll().requestMatchers("/actuator/**").permitAll().anyRequest().authenticated())
      .oauth2ResourceServer(o->o.jwt(j->j.decoder(d).jwtAuthenticationConverter(c))); return http.build();
  }
}
