package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import com.eating.jinwoo.repository.memberRepository.MemoryMemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MemberServiceTests {

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
    @DisplayName("회원가입")
    class Register {
        @Test
        @DisplayName("회원가입 성공 - 사진 없이")
        void register_NoPic_O() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);

            // when
            memberService.joinOrLogin(joinMember);
            // then
            Member savedMember = memberRepository.findById(1L).get();
            checkMember(joinMember, savedMember);
        }
        @Test
        @DisplayName("회원가입 성공이 - 사진 있이")
        void register_YesPic_O() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);
            MultipartFile file = new MockMultipartFile("mock", (byte[]) null);
            joinMember.setProfile(file);

            // when
            memberService.joinOrLogin(joinMember);

            // then
            Member savedMember = memberRepository.findById(1L).get();
            checkMember(joinMember, savedMember);
        }
    }
    @Nested
    @DisplayName("로그인")
    class Login {
        @Test
        @DisplayName("로그인 성공")
        void login_O() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);

            // when
            memberService.joinOrLogin(joinMember);
            Member afterJoin = memberRepository.findById(1L).get();
            memberService.logout();
            memberService.joinOrLogin(joinMember);
            Member afterLogin = memberRepository.findById(1L).get();

            // given
            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
            assertThat(principal).isNotNull();
            assertThat(principal.getPrincipal().toString()).isEqualTo("jinwoo");
            assertThat(principal.getAuthorities().toString()).isEqualTo("[ROLE_USER]");

            assertThat(afterJoin).isEqualTo(afterLogin);
        }
        @Test
        @DisplayName("로그인 했는데 또 로그인 하려는 경우")
        void login_X_LoginAgain() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);

            // when
            memberService.joinOrLogin(joinMember);

            // given
            Exception exception = assertThrows(EatingException.class, () -> memberService.joinOrLogin(joinMember));
            assertThat(exception.getMessage()).isEqualTo("이미 로그인 된 상태입니다.");
        }
    }
    @Nested
    @DisplayName("로그아웃")
    class Logout {
        @Test
        @DisplayName("로그아웃 성공")
        void logout_O() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);

            // when
            memberService.joinOrLogin(joinMember);
            memberService.logout();

            // given
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication principal = context.getAuthentication();
            assertThat(principal).isNull();
        }
        @Test
        @DisplayName("로그인 안 되어 있는데 또 로그아웃 하려는 경우")
        void logout_X_LogoutAgain() {
            // given
            memberService = new MemberService(memberRepository, passwordEncoder);
            // when

            // given
            Exception exception = assertThrows(EatingException.class, memberService::logout);
            assertThat(exception.getMessage()).isEqualTo("이미 로그아웃 된 상태입니다.");
        }
    }
    private void checkMember(MemberDTO.JoinOrLogin joinMember, Member savedMember) {
        int num = 0;
        for (boolean the_category : savedMember.getCategory().getCategories()) {
            if(the_category) num += 1;
        }
        assertThat(savedMember.getKakaoId()).isEqualTo(joinMember.getKakaoId());
        assertThat(savedMember.getLocation().getLongitude()).isEqualTo(joinMember.getLongitude());
        assertThat(savedMember.getLocation().getLatitude()).isEqualTo(joinMember.getLatitude());
        assertThat(savedMember.getLocation().getAddress()).isEqualTo(joinMember.getAddress());
        assertThat(savedMember.getNickname()).isEqualTo(savedMember.getNickname());
        if (joinMember.getProfile() == null){
            assertThat(savedMember.getProfileUrl()).isNull();
        } else {
            assertThat(savedMember.getProfileUrl()).isEqualTo("profile_url");
        }
        assertThat(savedMember.getPassword()).isEqualTo("encoded");
        assertThat(savedMember.getSugarScore()).isEqualTo(0.0);
        assertThat(savedMember.getTimeGood()).isEqualTo(0);
        assertThat(savedMember.getFastAnswer()).isEqualTo(0);
        assertThat(savedMember.getNiceGuy()).isEqualTo(0);
        assertThat(savedMember.getFoodDivide()).isEqualTo(0);
        assertThat(savedMember.getTotalCount()).isEqualTo(0);
        assertThat(num).isEqualTo(savedMember.getCategory().getCategories().size());
    }
}
