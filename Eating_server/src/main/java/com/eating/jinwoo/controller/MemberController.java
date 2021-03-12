package com.eating.jinwoo.controller;

import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.dto.ResponseDTO;
import com.eating.jinwoo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    public ResponseDTO<String> joinOrLogin(@RequestBody MemberDTO.JoinOrLogin userInfo) {
        memberService.joinOrLogin(userInfo);
        return new ResponseDTO<String>(HttpStatus.OK, "dddd", null);
    }
}
