package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.Category;
import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.Post;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import com.eating.jinwoo.repository.memberRepository.MemoryMemberRepository;
import com.eating.jinwoo.repository.postRepository.MemoryPostRepository;
import com.eating.jinwoo.repository.postRepository.PostRepository;
import org.junit.jupiter.api.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PostServiceTests {
    private static MemberService memberService;
    private MemberRepository memberRepository;
    private static PasswordEncoder passwordEncoder;
    private MemberDTO.JoinOrLogin joinMember;
    private static PostService postService;
    private PostRepository postRepository;
    PostDTO.getPost postInfo = new PostDTO.getPost();

    @BeforeAll
    static void beforeAll() {
        passwordEncoder = mock(PasswordEncoder.class);
    }

    @BeforeEach
    void beforeEach() {
        joinMember = MemberDTO.JoinOrLogin.builder()
                .kakaoId("jinwoo").nickname("nick").address("where").profile(null).longitude(11.11).latitude(22.22).build();
        memberRepository = new MemoryMemberRepository();
        postRepository = new MemoryPostRepository();
        when(passwordEncoder.encode(joinMember.getKakaoId())).thenReturn("encoded");

        postInfo.setTitle("제목");
        postInfo.setDescription("내용");
        postInfo.setCategory(0);
        postInfo.setChatLink("link");
        postInfo.setFoodLink("link2");
        postInfo.setMeetPlace(0);
        postInfo.setDeliveryFeeByHost(0);
        postInfo.setMemberCountLimit(10);
        postInfo.setOrderTime(LocalDateTime.now());
    }

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
        postRepository.deleteAll();
        SecurityContextHolder.clearContext();
    }

    @Test
    @DisplayName("post write success")
    void writePost_O() {
        // given
        memberService = new MemberService(memberRepository, passwordEncoder);
        memberService.joinOrLogin(joinMember);

        // when
        postService = new PostService(postRepository, memberRepository);
        postService.writePost(postInfo);
        Member found_member = memberRepository.findByKakaoId("jinwoo").get();

        // then
        Post find_post = postRepository.findById(1L).get();
        assertThat(find_post.getHost()).isEqualTo(found_member);
    }

    @Test
    @DisplayName("post write fail - no user")
    void writePost_X() {
        // given
        memberService = new MemberService(memberRepository, passwordEncoder);
//        memberService.joinOrLogin(joinMember);

        // when
        postService = new PostService(postRepository, memberRepository);
//        postService.writePost(postInfo);

        // then
        Exception e = assertThrows(EatingException.class, () -> postService.writePost(postInfo));
        assertThat(e.getMessage()).isEqualTo("회원이 아닙니다.");
    }

    @Test
    @DisplayName("post delete success")
    void delete_post_O() {
        //given
        memberService = new MemberService(memberRepository, passwordEncoder);
        memberService.joinOrLogin(joinMember);
        postService = new PostService(postRepository, memberRepository);
        postService.writePost(postInfo);

        //when
        Iterable<Post> postAll = postRepository.findAll();
        List<Post> posts = new ArrayList<>();
        postAll.forEach(posts::add);

        assertThat(posts.size()).isEqualTo(1);

        //then
        postService.deletePost(1L);
        Post post = postRepository.findById(1L).get();
        assertThat(post.getDeletedDate()).isNotNull();
    }

    @Test
    @DisplayName("post delete fail")
    void delete_post_X() {
        //given
        memberService = new MemberService(memberRepository, passwordEncoder);
        memberService.joinOrLogin(joinMember);
        postService = new PostService(postRepository, memberRepository);
//        postService.writePost(postInfo);
        //when

        //then
        EatingException e = assertThrows(EatingException.class, () -> postService.deletePost(1L));
        assertThat(e.getMessage()).isEqualTo("삭제할 게시글이 없습니다.");
    }

    @Test
    @DisplayName("post favorite success")
    void favorite_post_O() {
        //given
        memberService = new MemberService(memberRepository, passwordEncoder);
        memberService.joinOrLogin(joinMember);
        postService = new PostService(postRepository, memberRepository);
        postService.writePost(postInfo);
        Post post = postRepository.findById(1L).get();

        //when
        postService.setFavorite(1L);
        //then
        Member member = memberRepository.findById(1L).get();
        assertThat(member.getFavoritePosts().size()).isEqualTo(1);
        assertThat(member.getFavoritePosts().get(0).getPost()).isEqualTo(post);
    }

    @Test
    @DisplayName("post favorite fail")
    void favorite_post_X() {
        //given
        memberService = new MemberService(memberRepository, passwordEncoder);
        memberService.joinOrLogin(joinMember);
        postService = new PostService(postRepository, memberRepository);

        //when

        //then
        EatingException e = assertThrows(EatingException.class, () -> postService.setFavorite(1L));
        assertThat(e.getMessage()).isEqualTo("게시글이 없습니다.");
    }
}
