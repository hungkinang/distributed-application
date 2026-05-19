package edu.bookingtour.svc.booking.config;

import edu.bookingtour.commons.jwt.JwtSupport;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class BookingSecurityConfig {

    @Bean
    public JwtDecoder jwtDecoder(JwtProperties props) {
        return JwtSupport.resourceServerJwtDecoder(props.getSecret());
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        return JwtSupport.jwtAuthenticationConverter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, JwtDecoder decoder, JwtAuthenticationConverter converter)
            throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a
                        .requestMatchers("/api/bookings/info", "/api/bookings/internal/**", "/actuator/**")
                        .permitAll()
                        .requestMatchers("/api/bookings/**")
                        .authenticated())
                .oauth2ResourceServer(
                        oauth -> oauth.jwt(jwt -> jwt.decoder(decoder).jwtAuthenticationConverter(converter)));
        return http.build();
    }
}
