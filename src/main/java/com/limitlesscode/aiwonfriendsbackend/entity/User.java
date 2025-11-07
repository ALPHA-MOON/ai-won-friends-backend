package com.limitlesscode.aiwonfriendsbackend.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id()
    private UUID user_id;

    @Column(nullable = false)
    private String hashed_password;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column()
    private String photo;

    @Column(nullable = false)
    private LocalDateTime created_at;

    @Column(nullable = false)
    private LocalDateTime updated_at;

    @Column()
    private LocalDateTime deleted_at;

    @Column()
    private LocalDateTime last_login;

    private String role = "USER";
}
