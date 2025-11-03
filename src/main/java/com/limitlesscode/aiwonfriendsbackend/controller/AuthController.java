package com.limitlesscode.aiwonfriendsbackend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
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
}
