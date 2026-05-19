package edu.bookingtour.svc.payment.config;
import edu.bookingtour.commons.jwt.JwtSupport;
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
@EnableConfigurationProperties({JwtProperties.class, InternalSecretProperties.class, BookingServiceProperties.class})
public class PaymentSecurityConfig {
    @Bean
    JwtDecoder jwtDecoder(JwtProperties p) {
        return JwtSupport.resourceServerJwtDecoder(p.getSecret());
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        return JwtSupport.jwtAuthenticationConverter();
    }

    @Bean
    SecurityFilterChain chain(HttpSecurity http, JwtDecoder decoder, JwtAuthenticationConverter converter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(
                        a -> a.requestMatchers("/api/payments/vnpay/callback", "/api/payments/info", "/actuator/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated())
                .oauth2ResourceServer(o -> o.jwt(j -> j.decoder(decoder).jwtAuthenticationConverter(converter)));
        return http.build();
    }
}
