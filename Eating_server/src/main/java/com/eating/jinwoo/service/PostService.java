package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.Category;
import com.eating.jinwoo.domain.MeetPlace;
import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.Post;
import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import com.eating.jinwoo.repository.postRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void writePost(PostDTO.getPost postInfo) {

        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        if (postInfo.getTitle() == null || postInfo.getDescription() == null ||
                postInfo.getChatLink() == null) {
            throw new EatingException("필수 정보를 입력해주세요");
        }

        // kakao id
        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();

        Post post = new Post();
        post.setTitle(postInfo.getTitle());
        post.setDescription(postInfo.getDescription());
        post.setChatLink(postInfo.getChatLink());
        post.setFoodLink(postInfo.getFoodLink());
        post.setOrderTime(postInfo.getOrderTime());
        post.setDeliveryFeeByHost(postInfo.getDeliveryFeeByHost() == 1 ? true : false);
        post.setMemberCountLimit(postInfo.getMemberCountLimit());
        post.setMeetPlace(MeetPlace.getEnumByValue(postInfo.getMeetPlace()));

        post.setHost(member);
        post.setCategory(new Category(postInfo.getCategory()));
        post.setLongitude(member.getLocation().getLongitude());
        post.setLatitude(member.getLocation().getLatitude());

        postRepository.save(post);
    }

    public void editPost(PostDTO.getPost postInfo, Long id) {
        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        // kakao id
        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();

        //
        postRepository.findById(id).ifPresentOrElse(
                (post) -> {
                    post.setTitle(postInfo.getTitle());
                    post.setDescription(postInfo.getDescription());
                    post.setChatLink(postInfo.getChatLink());
                    post.setFoodLink(postInfo.getFoodLink());
                    post.setOrderTime(postInfo.getOrderTime());
                    post.setDeliveryFeeByHost(postInfo.getDeliveryFeeByHost() == 1 ? true : false);
                    post.setMemberCountLimit(postInfo.getMemberCountLimit());
                    post.setMeetPlace(MeetPlace.getEnumByValue(postInfo.getMeetPlace()));

                    post.setHost(member);
                    post.setCategory(new Category(postInfo.getCategory()));
                    post.setLongitude(member.getLocation().getLongitude());
                    post.setLatitude(member.getLocation().getLatitude());
                }, () -> {throw new EatingException("게시글이 없습니다.");}
        );

    }

    public void deletePost(Long id) {
        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        Post post = postRepository.findById(id).get();
        post.setDeletedDate(LocalDate.now());
    }
}
