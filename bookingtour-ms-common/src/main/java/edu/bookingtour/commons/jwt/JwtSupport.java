package edu.bookingtour.commons.jwt;

import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/** HS256 JWT dùng chung svc-auth / resource server — secret → SHA-256 → key cố định 256 bit. */
public final class JwtSupport {

    private JwtSupport() {}

    public static SecretKey hmacSha256Key(String secret) {
        byte[] digest;
        try {
            digest = MessageDigest.getInstance("SHA-256").digest(secret.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
        return Keys.hmacShaKeyFor(digest);
    }

    public static JwtDecoder resourceServerJwtDecoder(String secret) {
        return NimbusJwtDecoder.withSecretKey(hmacSha256Key(secret))
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
    }

    public static JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter g = new JwtGrantedAuthoritiesConverter();
        g.setAuthorityPrefix("");
        g.setAuthoritiesClaimName("authorities");
        JwtAuthenticationConverter c = new JwtAuthenticationConverter();
        c.setJwtGrantedAuthoritiesConverter(g);
        return c;
    }
}
