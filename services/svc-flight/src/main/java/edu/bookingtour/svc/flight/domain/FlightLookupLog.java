package edu.bookingtour.svc.flight.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "flight_lookup_log")
public class FlightLookupLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String origin;
    private String destination;

    @Column(name = "depart_date")
    private LocalDate departDate;

    private Double price;

    @Column(name = "raw_json", columnDefinition = "MEDIUMTEXT")
    private String rawJson;

    @Column(name = "created_at")
    private Instant createdAt;

    @jakarta.persistence.PrePersist
    void pre() {
        if (createdAt == null) {
            createdAt = Instant.now();
        }
    }
}
