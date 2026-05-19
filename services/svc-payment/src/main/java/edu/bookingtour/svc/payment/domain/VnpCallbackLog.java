package edu.bookingtour.svc.payment.domain;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.time.Instant;
@Getter @Setter @Entity @Table(name = "vnp_callback_log")
public class VnpCallbackLog {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(name = "txn_ref", nullable = false) private String txnRef;
    @Column(name = "response_code") private String responseCode;
    @Column(name = "verified", nullable = false) private boolean verified;
    @Column(name = "payload_json", columnDefinition = "MEDIUMTEXT") private String payloadJson;
    @Column(name = "created_at") private Instant createdAt;
    @PrePersist void pre() { if (createdAt == null) createdAt = Instant.now(); }
}
