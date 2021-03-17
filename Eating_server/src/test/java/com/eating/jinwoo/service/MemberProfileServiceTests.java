package com.eating.jinwoo.service;

import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import com.eating.jinwoo.repository.memberRepository.MemoryMemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MemberProfileServiceTests {

    private static MemberService memberService;
    private MemberRepository memberRepository;
    private static PasswordEncoder passwordEncoder;
    private MemberDTO.JoinOrLogin joinMember;

    @BeforeAll
    static void beforeAll() {
        passwordEncoder = mock(PasswordEncoder.class);
    }
    @BeforeEach
    void beforeEach() {
        joinMember = MemberDTO.JoinOrLogin.builder()
                .kakaoId("jinwoo").nickname("nick").address("where").profile(null).longitude(11.11).latitude(22.22).build();
        memberRepository = new MemoryMemberRepository();
        when(passwordEncoder.encode(joinMember.getKakaoId())).thenReturn("encoded");
    }
    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Nested
    @DisplayName("프로필")
    class Profile {
        @Test
        @DisplayName("프로필 가져오기 성공")
        void profile_get_O() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);
            memberService.joinOrLogin(joinMember);

            // when
            MemberDTO.GetProfile memberProfile = memberService.getProfile();
            Member savedMember = memberRepository.findById(1L).get();

            // then
            checkMember(memberProfile, savedMember);
        }
        @Test
        @DisplayName("프로필 가져오기 성공 - review가 3개 이상일 때")
        void profile_get_O_multiple_reviews() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);
            MultipartFile file = new MockMultipartFile("mock", (byte[]) null);
            joinMember.setProfile(file);

            // when
            memberService.joinOrLogin(joinMember);

            // then
            Member savedMember = memberRepository.findById(1L).get();
        }
        @Test
        @DisplayName("프로필 가져오기 실패 - 로그인 X")
        void profile_get_X() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);
            MultipartFile file = new MockMultipartFile("mock", (byte[]) null);
            joinMember.setProfile(file);

            // when
            memberService.joinOrLogin(joinMember);

            // then
            Member savedMember = memberRepository.findById(1L).get();
        }
    }
    private void checkMember(MemberDTO.GetProfile joinMember, Member savedMember) {
        assertThat(savedMember.getNickname()).isEqualTo(savedMember.getNickname());
        if (joinMember.getProfileUrl() == null){
            assertThat(savedMember.getProfileUrl()).isNull();
        } else {
            assertThat(savedMember.getProfileUrl()).isEqualTo("profile_url");
        }
        assertThat(savedMember.getSugarScore()).isEqualTo(0.0);
        assertThat(savedMember.getTimeGood()).isEqualTo(0);
        assertThat(savedMember.getFastAnswer()).isEqualTo(0);
        assertThat(savedMember.getNiceGuy()).isEqualTo(0);
        assertThat(savedMember.getFoodDivide()).isEqualTo(0);
        assertThat(savedMember.getTotalCount()).isEqualTo(0);
    }
}
