package com.eating.jinwoo.controller;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.dto.ResponseDTO;
import com.eating.jinwoo.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Api(tags={"Member"})
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member")
    @ApiOperation(value = "회원가입/로그인 동시 처리")
    public ResponseDTO<String> joinOrLogin(@RequestBody MemberDTO.JoinOrLogin userInfo) {
        memberService.joinOrLogin(userInfo);
        return new ResponseDTO<>(HttpStatus.OK, "로그인 성공", null);
    }

    @PostMapping("/member/out")
    @ApiOperation(value = "로그아웃")
    public ResponseDTO<String> logout() {
        memberService.logout();
        return new ResponseDTO<>(HttpStatus.OK, "로그아웃 성공", null);
    }

    @GetMapping("/member")
    @ApiOperation(value = "프로필 가져오기")
    public ResponseDTO<MemberDTO.GetProfile> getProfile() {
        MemberDTO.GetProfile memberProfile = memberService.getProfile();
        return new ResponseDTO<>(HttpStatus.OK, "프로필 정보 가져오기", memberProfile);
    }
}
