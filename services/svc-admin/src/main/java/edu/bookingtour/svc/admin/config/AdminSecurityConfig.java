package edu.bookingtour.svc.admin.config;

import edu.bookingtour.commons.jwt.JwtSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
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
@EnableConfigurationProperties({JwtProperties.class, AdminBackendProperties.class})
@RequiredArgsConstructor
public class AdminSecurityConfig {

    private final JwtProperties jwtProps;

    @Bean
    JwtDecoder jwtDecoder() {
        return JwtSupport.resourceServerJwtDecoder(jwtProps.getSecret());
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        return JwtSupport.jwtAuthenticationConverter();
    }

    @Bean
    SecurityFilterChain chain(HttpSecurity http, JwtDecoder decoder, JwtAuthenticationConverter converter)
            throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(a -> a.requestMatchers("/api/admin/info", "/actuator/**")
                        .permitAll()
                        .requestMatchers("/api/admin/**")
                        .authenticated())
                .oauth2ResourceServer(o -> o.jwt(j -> j.decoder(decoder).jwtAuthenticationConverter(converter)));
        return http.build();
    }
}
