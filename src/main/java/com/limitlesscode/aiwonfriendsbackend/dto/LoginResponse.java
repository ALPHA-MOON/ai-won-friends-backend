package com.limitlesscode.aiwonfriendsbackend.dto;

public record LoginResponse(
        String accessToken,
        long expiresInSec
) {
}
