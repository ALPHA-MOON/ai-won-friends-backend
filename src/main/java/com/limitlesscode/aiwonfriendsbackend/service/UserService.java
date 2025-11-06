package com.limitlesscode.aiwonfriendsbackend.service;

import com.limitlesscode.aiwonfriendsbackend.dto.UserRegisterRequest;
import com.limitlesscode.aiwonfriendsbackend.entity.User;
import com.limitlesscode.aiwonfriendsbackend.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User register(UserRegisterRequest request) {

        User user = new User(
            UUID.randomUUID(),
            passwordEncoder.encode(request.password()),
            request.email(),
            request.name(),
            request.photo(),
            LocalDateTime.now(),
            LocalDateTime.now(),
            null,
                null


        );

        return userRepository.save(user);
    }
}
