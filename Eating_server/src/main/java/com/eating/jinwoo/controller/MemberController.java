package com.eating.jinwoo.controller;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.dto.ResponseDTO;
import com.eating.jinwoo.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Api(tags={"Member"})
@RestController

@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/member/join")
    @ApiOperation(value = "회원가입 처리")
    public ResponseDTO<String> join(@RequestBody MemberDTO.Join userInfo) {
        memberService.join(userInfo);
        return new ResponseDTO<>(HttpStatus.OK, "회원가입 후 로그인 성공", null);
    }

    @PostMapping("/member/login")
    @ApiOperation(value = "로그인 처리")
    public ResponseDTO<MemberDTO.LoginResponse> login(@RequestBody MemberDTO.Login userInfo) {
        MemberDTO.LoginResponse result = memberService.login(userInfo);
        return new ResponseDTO<>(HttpStatus.OK, "로그인 성공", result);
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

    @PutMapping("/member")
    @ApiOperation(value = "프로필(닉네임) 수정하기")
    public ResponseDTO<String> editProfile(@RequestBody MemberDTO.EditProfile editInfo) {
        memberService.editProfile(editInfo);
        return new ResponseDTO<>(HttpStatus.OK, "프로필 수정완료", null);
    }

    @PutMapping("/member/address")
    @ApiOperation(value = "주소 수정하기")
    public ResponseDTO<String> editAddress(@RequestBody MemberDTO.EditAddress editInfo) {
        memberService.editAddress(editInfo);
        return new ResponseDTO<>(HttpStatus.OK, "주소 수정완료", null);
    }

    @PutMapping("/member/filter")
    @ApiOperation(value = "필터 수정하기")
    public ResponseDTO<String> editAddress(@RequestBody MemberDTO.EditFilter editInfo) {
        memberService.editFilter(editInfo);
        return new ResponseDTO<>(HttpStatus.OK, "필터 수정완료", null);
    }

//    @PutMapping("member/participate/{postID}")
//    @ApiOperation(value = "잇팅 참여하기")
//    public ResponseDTO<String> participate(@PathVariable("postID") Long id) {
//        memberService.participate(id);
//        return new ResponseDTO<>(HttpStatus.OK, "참여 완료", null);
//    }

//    @PutMapping("member/unparticipate/{postID}")
//    @ApiOperation(value = "잇팅 참여 취소하기")
//    public ResponseDTO<String> unparticipate(@PathVariable("postID") Long id) {
//        memberService.unparticipate(id);
//        return new ResponseDTO<>(HttpStatus.OK, "참여 취소 완료", null);
//    }
}
