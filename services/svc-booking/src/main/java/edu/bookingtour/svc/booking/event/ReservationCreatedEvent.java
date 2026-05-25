package edu.bookingtour.svc.booking.event;

import java.time.Instant;
import java.util.UUID;

public record ReservationCreatedEvent(
        UUID bookingUuid,
        int bookingId,
        int userId,
        int chuyenDiId,
        String email,
        String hoTen,
        Integer soLuong,
        Double tongGia,
        Instant at) {}
