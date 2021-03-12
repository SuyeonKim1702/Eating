package com.eating.jinwoo.service;

import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.repository.MemberRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;


public class MemberServiceTests {

    private MemberService memberService;
    private MemberRepository memberRepository;
    private MemberDTO.JoinOrLogin joinMember;

    @BeforeAll
    void beforeAll() {
        memberRepository = mock(MemberRepository.class);
        joinMember = MemberDTO.JoinOrLogin.builder()
                .id(1L).kakao_id("jinwoo").nickname("nick").address("where").profile(null).longitude(11.11).latitude(22.22).build();
    }

    @Nested
    @DisplayName("회원가입")
    class register {
        @Test
        @DisplayName("회원가입 성공")
        void register_O() {
            // given
            when(memberRepository.findById(joinMember.getId())).thenReturn(Optional.empty());
            memberService = new MemberService(memberRepository);
            // when
            memberService.joinOrLogin(joinMember);
            // then
            verify(memberRepository, times(1)).findById(1L);
        }
    }
    @Nested
    @DisplayName("로그인")
    class login {
        @Test
        @DisplayName("로그인 성공")
        void login_O() {

        }
        @Test
        @DisplayName("회원가입 하지 않았는데 로그인을 원하는 경우")
        void login_X_NoUser() {

        }
        @Test
        @DisplayName("로그인 했는데 또 로그인 하려는 경우")
        void login_X_LoginAgain() {

        }
    }
    @Nested
    @DisplayName("로그아웃")
    class logout {
        @Test
        @DisplayName("로그아웃 성공")
        void logout_O() {

        }
        @Test
        @DisplayName("로그인 안 되어 있는데 또 로그아웃 하려는 경우")
        void logout_X_LogoutAgain() {

        }
    }



}
