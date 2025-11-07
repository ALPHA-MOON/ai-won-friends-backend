package com.limitlesscode.aiwonfriendsbackend.controller;

import com.limitlesscode.aiwonfriendsbackend.dto.UserRegisterRequest;
import com.limitlesscode.aiwonfriendsbackend.dto.UserInfoResponse;
import com.limitlesscode.aiwonfriendsbackend.entity.User;
import com.limitlesscode.aiwonfriendsbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //유저등록
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE) //어떤 미디어 타입을 받을 것인지 명시
    public ResponseEntity<?> register(@Valid @ModelAttribute UserRegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.status(201).body(UserInfoResponse.toResponse(user));
    }

    //유저조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        User user = userService.getUser(id);
        return ResponseEntity.status(200).body(UserInfoResponse.toResponse(user));
    }

    //정보수정
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id) {
        return ResponseEntity.status(200).body("id : " + id );
    }

    //유저삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id) {
        return ResponseEntity.status(200).body("id : " + id );
    }
}
