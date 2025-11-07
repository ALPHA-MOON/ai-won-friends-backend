package com.limitlesscode.aiwonfriendsbackend.controller;

import com.limitlesscode.aiwonfriendsbackend.dto.VerifyEmailRequest;
import com.limitlesscode.aiwonfriendsbackend.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    //로그인
    @PostMapping("/login")
    public String login() {
        return "login";
    }

    //로그아웃
    @PostMapping("/logout")
    public String logout() {
        return "logout";
    }

    //이메일 인증번호 보내기
    @PostMapping("/verify/send-email")
    public ResponseEntity<?> sendEmail(@RequestBody VerifyEmailRequest request) {
        authService.sendVerificationCodeToEmail(request.email());
        return ResponseEntity.status(204).build();
    }

    //이메일 인증번호 검증하기
    @GetMapping("verify/token")
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
