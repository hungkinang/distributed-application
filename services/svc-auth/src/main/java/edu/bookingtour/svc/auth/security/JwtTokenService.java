package edu.bookingtour.svc.auth.security;

import edu.bookingtour.commons.jwt.JwtSupport;
import edu.bookingtour.svc.auth.config.JwtProperties;
import io.jsonwebtoken.Jwts;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class JwtTokenService {

    private final JwtProperties props;

    public JwtTokenService(JwtProperties props) {
        this.props = props;
    }

    public String issueToken(int userId, String username,
            Iterable<? extends GrantedAuthority> authorities) {
        long ttlMs = props.getAccessTokenTtlMinutes() * 60 * 1000;
        Date now = new Date();
        var authStrings = StreamSupport.stream(authorities.spliterator(), false)
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        return Jwts.builder()
                .subject(String.valueOf(userId))
                .claim("preferred_username", username)
                .claim("authorities", authStrings)
                .issuedAt(now)
                .expiration(new Date(now.getTime() + ttlMs))
                .signWith(JwtSupport.hmacSha256Key(props.getSecret()))
                .compact();
    }
}
