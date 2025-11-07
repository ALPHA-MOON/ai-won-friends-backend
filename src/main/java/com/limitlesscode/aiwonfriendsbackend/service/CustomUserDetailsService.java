package com.limitlesscode.aiwonfriendsbackend.service;

import com.limitlesscode.aiwonfriendsbackend.entity.User;
import com.limitlesscode.aiwonfriendsbackend.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 여기서는 username = email로 가정
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("No user: " + username));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getHashed_password()) // BCrypt 인코딩된 해시
                .roles(user.getRole())
                .build();
    }
}
