package edu.bookingtour.svc.booking.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record InternalPaymentPayload(@NotNull Long reservationId, @NotBlank String status) {}
