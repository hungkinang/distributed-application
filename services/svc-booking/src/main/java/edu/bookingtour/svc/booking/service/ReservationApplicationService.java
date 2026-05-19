package edu.bookingtour.svc.booking.service;

import edu.bookingtour.svc.booking.api.dto.CreateReservationRequest;
import edu.bookingtour.svc.booking.api.dto.ReservationResponse;
import edu.bookingtour.svc.booking.domain.Reservation;
import edu.bookingtour.svc.booking.event.ReservationCreatedEvent;
import edu.bookingtour.svc.booking.repo.ReservationRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationApplicationService {

    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher events;

    public ReservationApplicationService(
            ReservationRepository reservationRepository,
            ApplicationEventPublisher events) {
        this.reservationRepository = reservationRepository;
        this.events = events;
    }

    @Transactional
    public ReservationResponse create(CreateReservationRequest req, int userId) {
        Reservation entity = new Reservation();
        entity.setUserId(userId);
        entity.setChuyenDiId(req.chuyenDiId());
        entity.setSoLuong(req.soLuong());
        entity.setNgayDat(LocalDate.now());
        entity.setCreatedAt(LocalDateTime.now());
        entity.setTrangThai("PENDING");
        entity.setHoTen(req.hoTen());
        entity.setEmail(req.email());
        entity.setSoDienThoai(req.soDienThoai());
        entity.setDiaChi(req.diaChi());
        entity.setGhiChu(req.ghiChu());
        entity.setTongGia(req.tongGia());
        entity.setMaGiamGiaId(req.maGiamGiaId());

        Reservation saved = reservationRepository.save(entity);

        events.publishEvent(
                new ReservationCreatedEvent(saved.getId(), userId, req.chuyenDiId(), req.tongGia(), Instant.now()));

        return map(saved);
    }

    public List<ReservationResponse> findMine(int userId) {
        return reservationRepository.findByUserIdOrderByIdDesc(userId).stream()
                .map(this::map)
                .toList();
    }

    public ReservationResponse findByIdForUser(int reservationId, int userId) {
        Reservation r =
                reservationRepository.findById(reservationId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (r.getUserId() == null || r.getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return map(r);
    }

    @Transactional
    public void applyInternalPayment(Long reservationId, String newStatus, String sharedToken, String expectedToken) {
        if (expectedToken == null || expectedToken.isBlank() || !expectedToken.equals(sharedToken)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "invalid service token");
        }
        String normalized = normalizePaymentStatus(newStatus);
        Reservation r =
                reservationRepository.findById(reservationId.intValue()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (!"PENDING".equalsIgnoreCase(r.getTrangThai())) {
            return;
        }
        r.setTrangThai(normalized);
    }

    private static String normalizePaymentStatus(String raw) {
        if (raw == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status required");
        }
        String s = raw.trim().toUpperCase();
        if ("PAID".equals(s) || "FAILED".equals(s)) {
            return s;
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "status must be PAID or FAILED");
    }

    private ReservationResponse map(Reservation r) {
        return new ReservationResponse(
                r.getId(),
                r.getUserId(),
                r.getChuyenDiId(),
                r.getSoLuong(),
                r.getTrangThai(),
                r.getHoTen(),
                r.getEmail(),
                r.getSoDienThoai(),
                r.getTongGia(),
                r.getNgayDat() != null ? r.getNgayDat().toString() : null,
                r.getCreatedAt() != null ? r.getCreatedAt().toString() : null);
    }
}
