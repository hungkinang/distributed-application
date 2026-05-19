package edu.bookingtour.svc.auth.api.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record LoginRequest(
        @JsonProperty("username") @JsonAlias("tenDangNhap") @NotBlank String username,
        @NotBlank String password) {}
