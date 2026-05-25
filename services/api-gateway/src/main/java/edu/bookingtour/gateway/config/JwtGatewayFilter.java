package edu.bookingtour.gateway.config;

import edu.bookingtour.commons.jwt.JwtSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class JwtGatewayFilter implements GlobalFilter, Ordered {

    private final JwtDecoder jwtDecoder;

    public JwtGatewayFilter(@Value("${jwt.secret}") String secret) {
        this.jwtDecoder = JwtSupport.resourceServerJwtDecoder(secret);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (isPublic(path)) {
            return chain.filter(exchange);
        }

        String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (auth == null || !auth.startsWith("Bearer ")) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }

        try {
            Jwt jwt = jwtDecoder.decode(auth.substring(7));
            ServerWebExchange mutated = exchange.mutate()
                    .request(r -> r.header("X-User-Id", jwt.getSubject()))
                    .build();
            return chain.filter(mutated);
        } catch (JwtException ex) {
            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
            return exchange.getResponse().setComplete();
        }
    }

    private boolean isPublic(String path) {
        if (path.startsWith("/api/auth/")) {
            return true;
        }
        if (path.contains("/actuator")) {
            return true;
        }
        if (path.startsWith("/api/tours") && !path.contains("/internal")) {
            return true;
        }
        return path.startsWith("/api/news")
                || path.startsWith("/api/flights")
                || path.startsWith("/api/contact");
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
