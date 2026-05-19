package edu.bookingtour.svc.admin.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

@Data
@ConfigurationProperties(prefix = "admin")
public class AdminBackendProperties {

    /** key = tên service, value = base URL (không có dấu / cuối). */
    private Map<String, String> backends = new HashMap<>();
}
