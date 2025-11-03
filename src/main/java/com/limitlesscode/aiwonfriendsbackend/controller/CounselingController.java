package com.limitlesscode.aiwonfriendsbackend.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/counseling")
public class CounselingController {
    //상담 요청하기
    @PostMapping()
    public String counseling() {
        return "counseling";
    }

    //기존 상담내용 조회하기
    @PostMapping("/{id}")
    public String getCounseling(@PathVariable String id) {
        return "getCounseling id: " + id ;
    }

    //상담목록 조회하기
    @GetMapping()
    public String getCounselingList() {
        return "getCounselingList";
    }

    //상담 내용 삭제하기
    @DeleteMapping("/{id}")
    public String deleteCounseling(@PathVariable String id) {
        return "deleteCounseling id: " + id;
    }
}
