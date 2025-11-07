package com.limitlesscode.aiwonfriendsbackend.controller;

import com.limitlesscode.aiwonfriendsbackend.dto.UserRegisterRequest;
import com.limitlesscode.aiwonfriendsbackend.dto.UserRegisterResponse;
import com.limitlesscode.aiwonfriendsbackend.entity.User;
import com.limitlesscode.aiwonfriendsbackend.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    //유저등록
    @PostMapping()
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequest request) {
        User user = userService.register(request);
        return ResponseEntity.status(201).body(UserRegisterResponse.toResponse(user));
    }

    //유저조회
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable String id) {
        return ResponseEntity.status(200).body("id : " + id );
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
