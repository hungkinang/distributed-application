package edu.bookingtour.svc.booking.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreateReservationRequest(
        @JsonProperty("chuyenDiId") @NotNull Integer chuyenDiId,
        @NotNull @Min(1) Integer soLuong,
        @NotBlank String hoTen,
        @NotBlank @Email String email,
        @NotBlank String soDienThoai,
        String diaChi,
        String ghiChu,
        @NotNull @Positive Double tongGia,
        Integer maGiamGiaId) {}
