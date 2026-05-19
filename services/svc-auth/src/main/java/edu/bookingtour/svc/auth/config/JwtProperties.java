package edu.bookingtour.svc.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;

    /** Thời hạn JWT (phút). */
    private long accessTokenTtlMinutes = 120;
}
