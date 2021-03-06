//package com.eating.jinwoo.service;
//
//import com.eating.jinwoo.common.EatingException;
//import com.eating.jinwoo.domain.Category;
//import com.eating.jinwoo.domain.Member;
//import com.eating.jinwoo.dto.MemberDTO;
//import com.eating.jinwoo.repository.memberRepository.MemberRepository;
//import com.eating.jinwoo.repository.memberRepository.MemoryMemberRepository;
//import org.junit.jupiter.api.*;
//import org.springframework.mock.web.MockMultipartFile;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.multipart.MultipartFile;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.mockito.Mockito.*;
//
//public class MemberServiceTests {
//
//    private static MemberService memberService;
//    private MemberRepository memberRepository;
//    private static PasswordEncoder passwordEncoder;
//    private MemberDTO.JoinOrLogin joinMember;
//
//    @BeforeAll
//    static void beforeAll() {
//        passwordEncoder = mock(PasswordEncoder.class);
//    }
//    @BeforeEach
//    void beforeEach() {
////        joinMember = MemberDTO.JoinOrLogin.builder()
////                .kakaoId("jinwoo").nickname("nick").address("where").profile(null).longitude(11.11).latitude(22.22).build();
//        joinMember = MemberDTO.JoinOrLogin.builder()
//                .kakaoId("jinwoo").nickname("nick").address("where").longitude(11.11).latitude(22.22).build();
//
//        memberRepository = new MemoryMemberRepository();
//        when(passwordEncoder.encode(joinMember.getKakaoId())).thenReturn("encoded");
//    }
//    @AfterEach
//    void afterEach() {
//        memberRepository.deleteAll();
//        SecurityContextHolder.clearContext();
//    }
//
//    @Nested
//    @DisplayName("Join Member")
//    class Register {
//        @Test
//        @DisplayName("Join Member Success - Profile X")
//        void register_NoPic_O() {
//            // given
//            memberService = new MemberService(memberRepository, passwordEncoder);
//
//            // when
//            memberService.joinOrLogin(joinMember);
//            // then
//            Member savedMember = memberRepository.findById(1L).get();
//            checkMember(joinMember, savedMember);
//        }
////        @Test
////        @DisplayName("Join Member Success - Profile O")
////        void register_YesPic_O() {
////            // given
////            memberService = new MemberService(memberRepository, passwordEncoder);
////            MultipartFile file = new MockMultipartFile("mock", (byte[]) null);
////            joinMember.setProfile(file);
////
////            // when
////            memberService.joinOrLogin(joinMember);
////
////            // then
////            Member savedMember = memberRepository.findById(1L).get();
////            checkMember(joinMember, savedMember);
////        }
//        @Test
//        @DisplayName("Join Member Success - Korean Nickname")
//        void register_O() {
//            // given
//            memberService = new MemberService(memberRepository, passwordEncoder);
//            joinMember.setNickname("?????????");
//
//            // when
//            memberService.joinOrLogin(joinMember);
//
//            // then
//            Member savedMember = memberRepository.findById(1L).get();
//            checkMember(joinMember, savedMember);
//        }
//        @Test
//        @DisplayName("Join Member Fail - Nickname String rule")
//        void register_X1() {
//            // given
//            memberService = new MemberService(memberRepository, passwordEncoder);
//
//            // when
//            joinMember.setNickname("???????????????????????????");
//            // then
//            EatingException e = assertThrows(EatingException.class, () -> memberService.joinOrLogin(joinMember));
//            assertThat(e.getMessage()).isEqualTo("????????? ????????? ????????? ???????????????.");
//
//            // when
//            joinMember.setNickname("aa");
//            // then
//            e = assertThrows(EatingException.class, () -> memberService.joinOrLogin(joinMember));
//            assertThat(e.getMessage()).isEqualTo("????????? ????????? ????????? ???????????????.");
//        }
//        @Test
//        @DisplayName("Join Member Fail - Nickname Duplicate rule")
//        void register_X2() {
//            // given
//            memberService = new MemberService(memberRepository, passwordEncoder);
//
//            // when
//            memberService.joinOrLogin(joinMember);
//            joinMember.setKakaoId("jinwoo2");
//            memberService.logout();
//
//            // then
//            EatingException e = assertThrows(EatingException.class, () -> memberService.joinOrLogin(joinMember));
//            assertThat(e.getMessage()).isEqualTo("?????? ???????????? ??????????????????.");
//        }
//    }
//    @Nested
//    @DisplayName("Login")
//    class Login {
//        @Test
//        @DisplayName("Login Success")
//        void login_O() {
//            // given
//            memberService = new MemberService(memberRepository, passwordEncoder);
//
//            // when
//            memberService.joinOrLogin(joinMember);
//            Member afterJoin = memberRepository.findById(1L).get();
//            memberService.logout();
//            memberService.joinOrLogin(joinMember);
//            Member afterLogin = memberRepository.findById(1L).get();
//
//            // given
//            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//            assertThat(principal).isNotNull();
//            assertThat(principal.getPrincipal().toString()).isEqualTo("jinwoo");
//            assertThat(principal.getAuthorities().toString()).isEqualTo("[ROLE_USER]");
//
//            assertThat(afterJoin).isEqualTo(afterLogin);
//        }
//        @Test
//        @DisplayName("Login Duplicate")
//        void login_X_LoginAgain() {
//            // given
//            memberService = new MemberService(memberRepository, passwordEncoder);
//
//            // when
//            memberService.joinOrLogin(joinMember);
//
//            // given
//            Exception exception = assertThrows(EatingException.class, () -> memberService.joinOrLogin(joinMember));
//            assertThat(exception.getMessage()).isEqualTo("?????? ????????? ??? ???????????????.");
//        }
//    }
//    @Nested
//    @DisplayName("Logout")
//    class Logout {
//        @Test
//        @DisplayName("Logout Success")
//        void logout_O() {
//            // given
//            memberService = new MemberService(memberRepository, passwordEncoder);
//
//            // when
//            memberService.joinOrLogin(joinMember);
//            memberService.logout();
//
//            // given
//            SecurityContext context = SecurityContextHolder.getContext();
//            Authentication principal = context.getAuthentication();
//            assertThat(principal).isNull();
//        }
//        @Test
//        @DisplayName("Logout Duplicate")
//        void logout_X_LogoutAgain() {
//            // given
//            memberService = new MemberService(memberRepository, passwordEncoder);
//            // when
//
//            // given
//            Exception exception = assertThrows(EatingException.class, memberService::logout);
//            assertThat(exception.getMessage()).isEqualTo("?????? ???????????? ??? ???????????????.");
//        }
//    }
//    private void checkMember(MemberDTO.JoinOrLogin joinMember, Member savedMember) {
//        int num = 0;
//        for (Category category : savedMember.getMemberCategory()){
//            num += 1;
//        }
//        assertThat(savedMember.getKakaoId()).isEqualTo(joinMember.getKakaoId());
//        assertThat(savedMember.getLocation().getLongitude()).isEqualTo(joinMember.getLongitude());
//        assertThat(savedMember.getLocation().getLatitude()).isEqualTo(joinMember.getLatitude());
//        assertThat(savedMember.getLocation().getAddress()).isEqualTo(joinMember.getAddress());
//        assertThat(savedMember.getNickname()).isEqualTo(savedMember.getNickname());
////        if (joinMember.getProfile() == null){
////            assertThat(savedMember.getProfileUrl()).isNull();
////        } else {
////            assertThat(savedMember.getProfileUrl()).isEqualTo("profile_url");
////        }
//        assertThat(savedMember.getPassword()).isEqualTo("encoded");
//        assertThat(savedMember.getSugarScore()).isEqualTo(50.0);
//        assertThat(savedMember.getTimeGood()).isEqualTo(0);
//        assertThat(savedMember.getFastAnswer()).isEqualTo(0);
//        assertThat(savedMember.getNiceGuy()).isEqualTo(0);
//        assertThat(savedMember.getFoodDivide()).isEqualTo(0);
//        assertThat(savedMember.getTotalCount()).isEqualTo(0);
//        assertThat(num).isEqualTo(10);
//    }
//}
