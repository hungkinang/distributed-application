package edu.bookingtour.commons.messaging;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

/** Payload bất đồng bộ: Booking Service → RabbitMQ → Email / Notification. */
public record BookingCreatedMessage(
        UUID bookingUuid,
        Integer bookingId,
        Integer userId,
        Integer chuyenDiId,
        String email,
        String hoTen,
        Integer soLuong,
        BigDecimal tongGia,
        Instant createdAt) {}
