package com.limitlesscode.aiwonfriendsbackend.dto;

import com.limitlesscode.aiwonfriendsbackend.entity.User;

import java.util.UUID;

public record UserRegisterResponse(
        UUID user_id,
        String email,
        String name,
        String photo
) {
    public static UserRegisterResponse toResponse(User user) {
        return new UserRegisterResponse(
                user.getUser_id(),
                user.getEmail(),
                user.getName(),
                user.getPhoto()
        );
    }
}
