package com.limitlesscode.aiwonfriendsbackend.service;

import com.limitlesscode.aiwonfriendsbackend.entity.EmailVerificationToken;
import com.limitlesscode.aiwonfriendsbackend.repository.EmailVerificationTokenRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JavaMailSender mailSender;
    private final EmailVerificationTokenRepository tokenRepository;

    @Value("${app.mail.from}")
    private String from;

    @Value("${app.mail.verify-base-url}")
    private String verifyBaseUrl;

    @Value("${app.mail.token-ttl-minutes}")
    private long tokenTtlMinutes;

    private static final SecureRandom RANDOM = new SecureRandom();

    public void sendVerificationCodeToEmail(String email) {
        //토큰 생성
        String token = generateUrlSafeToken(32);

        //레파지토리 저장
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(tokenTtlMinutes);
        tokenRepository.save(
                EmailVerificationToken.builder()
                        .email(email)
                        .token(token)
                        .expiredAt(expiresAt)
                        .build()
        );

        //인증이메일 html 만들기
        String subject = "[AI-Won-Friends] 이메일 인증을 완료해 주세요";
        String verifyLink = verifyBaseUrl + "?token=" + token;
        String html = """
                <div style="font-family:sans-serif;line-height:1.6">
                  <h2>이메일 인증</h2>
                  <p>아래 버튼을 클릭하면 이메일 인증이 완료됩니다.</p>
                  <p><a href="%s" style="display:inline-block;padding:12px 18px;border-radius:8px;text-decoration:none;border:1px solid #333">이메일 인증하기</a></p>
                  <p>유효시간: %d분</p>
                </div>
                """.formatted(verifyLink, tokenTtlMinutes);

        //인증이메일 보내기
        try {
            MimeMessage message = mailSender.createMimeMessage();
            // 멀티파트 true, UTF-8
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(from);
            helper.setTo(email);
            helper.setSubject(subject);
            helper.setText(html, true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("메일 발송 실패", e);
        }
    }

    @Transactional
    public void verifyEmail(String token) {
        var entity = tokenRepository.findByToken(token)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰"));

        if (entity.isExpired()) {
            throw new IllegalStateException("만료된 토큰");
        }

        if (entity.isUsed()){
            throw new IllegalStateException("사용된 토큰");
        }

        entity.markUsed(); // 1회용 처리
        // 여기에서: 사용자 계정의 emailVerified=true 로 반영하는 로직을 이어서 작성
    }

    private String generateUrlSafeToken(int bytes) {
        byte[] buf = new byte[bytes];
        RANDOM.nextBytes(buf);
        // URL-safe Base64 (패딩 제거)
        return Base64.encodeBase64URLSafeString(buf);
    }
}
