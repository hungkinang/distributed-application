package edu.bookingtour.svc.auth.api.dto;

public record TokenResponse(
        String accessToken,
        String tokenType,
        long expiresInSeconds,
        Integer userId,
        String username) {}
