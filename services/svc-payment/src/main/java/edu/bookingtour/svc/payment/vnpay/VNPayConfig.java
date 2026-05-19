package edu.bookingtour.svc.payment.vnpay;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
@Component
public class VNPayConfig {
    @Value("${vnp.pay-url}")
    public String vnp_PayUrl;
    @Value("${vnp.return-url}")
    public String vnp_Returnurl;
    @Value("${vnp.tmn-code}")
    public String vnp_TmnCode;
    @Value("${vnp.hash-secret}")
    public String vnp_HashSecret;

    public String hashAllFields(Map<String, String> fields) throws UnsupportedEncodingException {
        List<String> fieldNames = new ArrayList<>(fields.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        boolean first = true;
        for (String fieldName : fieldNames) {
            String fieldValue = fields.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                if (!first) hashData.append('&');
                hashData.append(fieldName).append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                first = false;
            }
        }
        return hmacSHA512(vnp_HashSecret, hashData.toString());
    }

    public String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) throw new NullPointerException();
            Mac hmac512 = Mac.getInstance("HmacSHA512");
            hmac512.init(new SecretKeySpec(key.getBytes(), "HmacSHA512"));
            byte[] result = hmac512.doFinal(data.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) sb.append(String.format("%02x", b & 0xff));
            return sb.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    public String getIpAddress(HttpServletRequest request) {
        try {
            String ip = request.getHeader("X-FORWARDED-FOR");
            return ip != null ? ip : request.getRemoteAddr();
        } catch (Exception e) {
            return "0.0.0.0";
        }
    }
}
