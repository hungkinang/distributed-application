package edu.bookingtour.svc.payment.api.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
public record BuildVnpayUrlRequest(
        @NotNull @Positive Long reservationId,
        @NotNull @Positive Long amountVnd,
        @NotBlank String orderInfo) {}
