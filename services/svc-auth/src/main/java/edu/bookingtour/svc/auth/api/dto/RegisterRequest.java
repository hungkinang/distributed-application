package edu.bookingtour.svc.auth.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequest(
        @JsonProperty("tenDangNhap") @JsonAlias("username") @NotBlank String tenDangNhap,
        @JsonProperty("email") @Email @NotBlank String email,
        @JsonProperty("matKhau") @JsonAlias("password") @NotBlank String matKhau,
        @JsonProperty("hoTen") @NotBlank String hoTen,
        @JsonProperty("confirmPassword") String confirmPassword
) {}
