package edu.bookingtour.svc.chat.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gemini")
public class GeminiProperties {
    private Api api = new Api();
    private String key = "";

    @Data
    public static class Api {
        private String url = "https://generativelanguage.googleapis.com/v1/models/gemini-1.5-flash:generateContent";
    }
}
