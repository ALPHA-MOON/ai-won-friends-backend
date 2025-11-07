package com.limitlesscode.aiwonfriendsbackend.dto;

import com.limitlesscode.aiwonfriendsbackend.entity.User;

import java.util.UUID;

public record UserInfoResponse(
        UUID user_id,
        String email,
        String name,
        String photo
) {
    public static UserInfoResponse toResponse(User user) {
        return new UserInfoResponse(
                user.getUser_id(),
                user.getEmail(),
                user.getName(),
                user.getPhoto()
        );
    }
}
