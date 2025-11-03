package com.limitlesscode.aiwonfriendsbackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    //유저등록
    @PostMapping()
    public ResponseEntity<?> register() {
        return ResponseEntity.status(201).build();
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
