package edu.bookingtour.svc.booking.event;

import java.time.Instant;

public record ReservationCreatedEvent(int bookingId, int userId, int chuyenDiId, double tongGia, Instant at) {}
