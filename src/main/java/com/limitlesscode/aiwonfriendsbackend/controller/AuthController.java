package com.limitlesscode.aiwonfriendsbackend.controller;

import com.limitlesscode.aiwonfriendsbackend.dto.LoginRequest;
import com.limitlesscode.aiwonfriendsbackend.dto.LoginResponse;
import com.limitlesscode.aiwonfriendsbackend.dto.VerifyEmailRequest;
import com.limitlesscode.aiwonfriendsbackend.helper.JwtHelper;
import com.limitlesscode.aiwonfriendsbackend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtHelper jwtHelper;

    //로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        //로그인 후 성공하면 이메일 반환
        String email = authService.validateUser(request);

        //이메일 정보로 쿠키 만들기
        String accessToken = jwtHelper.createAccessToken(email);
        String refreshToken = jwtHelper.createRefreshToken(email);

        //refresh 토큰은 보안설정해서 쿠키에 담기
        ResponseCookie refreshCookie = ResponseCookie.from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/api/auth/refresh")
                .maxAge(Duration.ofSeconds(jwtHelper.getRefreshTtlSec()))
                .build();

        //access 토큰은 responseDto로 body에 담기
        LoginResponse response = new LoginResponse(accessToken, jwtHelper.getAccessTtlSec());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshCookie.toString())
                .body(response);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        ResponseCookie deleteCookie = ResponseCookie.from("refresh_token", "")
                .httpOnly(true)
                .secure(true)
                .sameSite("None")
                .path("/api/auth")
                .maxAge(0)
                .build();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, deleteCookie.toString())
                .build();
    }
    //refresh

    //이메일 인증번호 보내기
    @PostMapping("/verify/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody VerifyEmailRequest request) {
        authService.sendVerificationCodeToEmail(request.email());
        return ResponseEntity.status(204).build();
    }

    //이메일 인증번호 검증하기
    @GetMapping("verify/email-token")
    public ResponseEntity<String> verifyEmail(@RequestParam String token) {
        try {
            authService.verifyEmail(token);

            String successHtml = """
        <html><body style="font-family: sans-serif; text-align:center; padding-top:40px;">
          <h2>✅ 이메일 인증이 완료되었습니다</h2>
          <p>이제 서비스로 돌아가 로그인할 수 있어요.</p>
        </body></html>
        """;

            return ResponseEntity.ok().body(successHtml);

        } catch (Exception e) {

            String failHtml = """
        <html><body style="font-family: sans-serif; text-align:center; padding-top:40px;">
          <h2>⚠️ 이메일 인증에 실패했습니다</h2>
          <p>만료되었거나 이미 사용된 링크일 수 있습니다.</p>
        </body></html>
        """;

            return ResponseEntity.badRequest().body(failHtml);
        }


    }
}
