package com.limitlesscode.aiwonfriendsbackend.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // H2 콘솔만 CSRF 예외
//                .csrf(csrf -> csrf.ignoringRequestMatchers(PathRequest.toH2Console()))
//                .csrf(csrf -> csrf.disable())
                .csrf(AbstractHttpConfigurer::disable)
                // H2 콘솔은 frame으로 뜨므로 sameOrigin 허용
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))

                .authorizeHttpRequests(auth -> auth
                        // H2 콘솔 경로 명시 허용
                        .requestMatchers(PathRequest.toH2Console()).permitAll()
                        .anyRequest().permitAll()
                );

        return http.build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        String defaultId = "bcrypt"; // 기본 알고리즘 식별자

        Map<String, PasswordEncoder> encoders = new HashMap<>();

        // strength가 12인 이유: 2^12만큼 연산을 반복함.
        // 10 -> 보통
        // 12 -> 보안성이 향상, 서버가 버틸 수 있으면 추천
        // 14이상 -> 고부하 환경에서는 비효율적, 로그인 지연 발생 가능
        encoders.put("bcrypt", new BCryptPasswordEncoder(12)); // work factor 12 (예시)
        encoders.put("argon2", Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8()); // 필요시 설정

        return new DelegatingPasswordEncoder(defaultId, encoders);
    }
}
