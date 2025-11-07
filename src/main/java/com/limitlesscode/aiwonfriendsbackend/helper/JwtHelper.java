package com.limitlesscode.aiwonfriendsbackend.helper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Component
@AllArgsConstructor
public class JwtHelper {
    //TODO: secret 환경변수로 대체
    private final Key accessKey  = Keys.hmacShaKeyFor("UXnLnIauG7sJhysQM1MoSe8BzJAS9BoCx1GSW22Jte4=".getBytes());
    private final Key refreshKey = Keys.hmacShaKeyFor("RJ7sDO4V8w/bUFTZ9rOVUdZyZDD2S0I45U+zJFSOr8E=".getBytes());

    // Access Token TTL (예: 15분)
    @Getter
    private final long accessTtlSec  = 15 * 60;
    // Refresh Token TTL (예: 14일)
    @Getter
    private final long refreshTtlSec = 14L * 24 * 60 * 60;

    public String createAccessToken(String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(accessTtlSec)))
                .signWith(accessKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(String subject) {
        Instant now = Instant.now();
        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plusSeconds(refreshTtlSec)))
                .signWith(refreshKey, SignatureAlgorithm.HS256)
                .compact();
    }
}
