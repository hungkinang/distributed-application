package edu.bookingtour.svc.payment.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
@Data
@ConfigurationProperties(prefix = "internal")
public class InternalSecretProperties { private String serviceToken = ""; }
