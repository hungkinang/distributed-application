package edu.bookingtour.svc.payment.api;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bookingtour.svc.payment.api.dto.BuildVnpayUrlRequest;
import edu.bookingtour.svc.payment.api.dto.BuildVnpayUrlResponse;
import edu.bookingtour.svc.payment.client.BookingPaymentClient;
import edu.bookingtour.svc.payment.domain.VnpCallbackLog;
import edu.bookingtour.svc.payment.repo.VnpCallbackLogRepository;
import edu.bookingtour.svc.payment.vnpay.VNPayConfig;
import edu.bookingtour.svc.payment.config.BookingServiceProperties;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
@RestController
@RequestMapping("/api/payments")
public class PaymentRestController {
    private final VNPayConfig vnp;
    private final BookingPaymentClient bookingClient;
    private final VnpCallbackLogRepository logs;
    private final ObjectMapper json = new ObjectMapper();
    private final String bookingBase;

    public PaymentRestController(
            VNPayConfig vnp,
            BookingPaymentClient bookingClient,
            VnpCallbackLogRepository logs,
            BookingServiceProperties bookingSvc) {
        this.vnp = vnp;
        this.bookingClient = bookingClient;
        this.logs = logs;
        this.bookingBase = bookingSvc.getBaseUrl();
    }

    @PostMapping("/vnpay/build-url")
    public BuildVnpayUrlResponse buildUrl(
            @Valid @RequestBody BuildVnpayUrlRequest body,
            @AuthenticationPrincipal Jwt jwt,
            HttpServletRequest request) throws Exception {
        if (jwt == null) throw new ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        int uid = Integer.parseInt(jwt.getSubject());

        Rt rs = fetchReservation(body.reservationId(), jwt.getTokenValue(), uid);

        String st = rs.trangThai == null ? "" : rs.trangThai;
        if (!"PENDING".equalsIgnoreCase(st)) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.CONFLICT, "booking not pending");
        }
        double booked = rs.tongGia == null ? -1 : rs.tongGia.doubleValue();
        if (Math.abs(booked - body.amountVnd().doubleValue()) > 1.0) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.BAD_REQUEST, "amount mismatch");
        }

        long amount = body.amountVnd() * 100L;
        String vnpTxnRef = String.valueOf(body.reservationId());

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", vnp.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnpTxnRef);
        vnp_Params.put("vnp_OrderInfo", body.orderInfo());
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnp.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp.getIpAddress(request));

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        vnp_Params.put("vnp_CreateDate", formatter.format(cld.getTime()));
        cld.add(Calendar.MINUTE, 15);
        vnp_Params.put("vnp_ExpireDate", formatter.format(cld.getTime()));

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder query = new StringBuilder();
        boolean first = true;
        for (String fieldName : fieldNames) {
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && !fieldValue.isEmpty()) {
                if (!first) query.append('&');
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));
                first = false;
            }
        }
        String vnp_SecureHash = vnp.hashAllFields(vnp_Params);
        String paymentUrl = vnp.vnp_PayUrl + "?" + query + "&vnp_SecureHash=" + vnp_SecureHash;
        return new BuildVnpayUrlResponse(paymentUrl);
    }

    private Rt fetchReservation(Long id, String bearerJwt, int userId) throws Exception {
        String url = bookingBase.replaceAll("/$", "") + "/api/bookings/reservations/" + id;
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerJwt);
        HttpEntity<Void> req = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        var resp = rt.exchange(url, org.springframework.http.HttpMethod.GET, req,
                ReservationDto.class);
        ReservationDto dto = resp.getBody();
        if (dto == null) throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        if (dto.userId == null || dto.userId != userId) {
            throw new ResponseStatusException(org.springframework.http.HttpStatus.NOT_FOUND);
        }
        return new Rt(dto.trangThai, dto.tongGia);
    }

    private record Rt(String trangThai, Double tongGia) {}

    /** DTO phản hồi của svc-booking (ReservationResponse là record JSON). */
    @SuppressWarnings("unused")
    private static class ReservationDto {
        public Integer userId;
        public String trangThai;
        public Double tongGia;
    }

    /** VNPay redirect browser — endpoint public. */
    @GetMapping("/vnpay/callback")
    public Map<String, Object> callback(HttpServletRequest request) throws Exception {
        Map<String, String> fields = new HashMap<>();
        for (Enumeration<String> params = request.getParameterNames(); params.hasMoreElements(); ) {
            String name = params.nextElement();
            String value = request.getParameter(name);
            if (value != null && !value.isEmpty()) fields.put(name, value);
        }

        boolean ok = fields.containsKey("vnp_ResponseCode");

        Map<String, String> signFields = new HashMap<>(fields);
        String cliHash = fields.get("vnp_SecureHash");
        signFields.remove("vnp_SecureHashType");
        signFields.remove("vnp_SecureHash");

        boolean verified = false;
        try {
            String ours = vnp.hashAllFields(signFields);
            verified = ours.equals(cliHash);
        } catch (Exception ignored) {
        }

        String txn = fields.getOrDefault("vnp_TxnRef", "?");
        String code = fields.getOrDefault("vnp_ResponseCode", "?");

        VnpCallbackLog log = new VnpCallbackLog();
        log.setTxnRef(txn);
        log.setResponseCode(code);
        log.setVerified(verified);
        log.setPayloadJson(json.writeValueAsString(fields));
        logs.save(log);

        if (verified && ok) {
            long rid = Long.parseLong(txn);
            String payStatus = "00".equals(code) ? "PAID" : "FAILED";
            try {
                bookingClient.notifyBookingPaid(rid, payStatus);
            } catch (Exception ex) {
                return Map.of("verified", verified, "responseCode", code, "txnRef", txn,
                        "notifyBookingError", String.valueOf(ex.getMessage()));
            }
        }

        return Map.of(
                "verified", verified,
                "responseCode", code,
                "txnRef", txn);
    }
}
