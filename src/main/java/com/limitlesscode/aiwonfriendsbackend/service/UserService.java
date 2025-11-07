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

        //TODO: 사진을 파일시스템에 저장하고 db에 url로 저장하는 로직 추가 필요
        String photoUrl = "https://example.com/photo/" + request.photo().getOriginalFilename();


        User user = new User(
            UUID.randomUUID(),
            passwordEncoder.encode(request.password()),
            request.email(),
            request.name(),
            photoUrl,
            LocalDateTime.now(),
            LocalDateTime.now(),
            null,
                null


        );

        return userRepository.save(user);
    }
}
