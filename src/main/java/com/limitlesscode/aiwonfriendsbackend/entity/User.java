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
    UUID user_id;

    @Column(nullable = false)
    String hashed_password;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String name;

    @Column()
    String photo;

    @Column(nullable = false)
    LocalDateTime created_at;

    @Column(nullable = false)
    LocalDateTime updated_at;

    @Column()
    LocalDateTime deleted_at;

    @Column()
    LocalDateTime last_login;
}
