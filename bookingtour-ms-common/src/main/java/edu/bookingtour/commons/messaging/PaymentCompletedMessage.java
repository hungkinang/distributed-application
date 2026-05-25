package edu.bookingtour.commons.messaging;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentCompletedMessage(
        UUID paymentUuid,
        UUID bookingUuid,
        Integer bookingId,
        String email,
        String status,
        BigDecimal amount,
        Instant paidAt) {}
