package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.*;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import com.eating.jinwoo.repository.postRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final PostRepository postRepository;

    public MemberDTO.LoginResponse login(MemberDTO.Login userInfo) {
        memberRepository.findByKakaoId(userInfo.getKakaoId())
                .ifPresentOrElse(
                        this::doLogin, () -> {
                            throw new EatingException("로그인 정보가 없습니다.");
                        }

                );
        MemberDTO.LoginResponse returnValue = new MemberDTO.LoginResponse();
        Member member = memberRepository.findByKakaoId(userInfo.getKakaoId()).get();
        String address = member.getLocation().getAddress().toString();
        returnValue.setAddress(address);
        returnValue.setLatitude(member.getLocation().getLatitude());
        returnValue.setLongitude(member.getLocation().getLongitude());
        return returnValue;
    }

    public void join(MemberDTO.Join userInfo) {
//        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//        if(principal != null){
//            if(principal.getPrincipal() != "anonymousUser"){
//                throw new EatingException("이미 로그인 된 상태입니다.");
//            }
//        }
        memberRepository.findByNickname(userInfo.getNickname()).ifPresent((member) -> {
            throw new EatingException("이미 존재하는 닉네임입니다.");
        });
        if (userInfo.getNickname().length() > 8 || userInfo.getNickname().length() < 3) {
            throw new EatingException("닉네임 글자수 제한을 지켜주세요.");
        }
        Member member = new Member();
        member.setKakaoId(userInfo.getKakaoId());
        member.setNickname(userInfo.getNickname());
        member.setPassword(passwordEncoder.encode(userInfo.getKakaoId()));

        Location location = new Location(userInfo.getAddress(), userInfo.getLongitude(), userInfo.getLatitude());
        member.setLocation(location);

        memberRepository.save(member);
        doLogin(member);
    }
    private void doLogin(Member member) {
        Authentication authentication
                = new UsernamePasswordAuthenticationToken(member.getKakaoId(), member.getPassword(),
                AuthorityUtils.createAuthorityList("ROLE_USER"));

        SecurityContext securityContext = SecurityContextHolder.getContext();
        securityContext.setAuthentication(authentication);
    }

    public void logout() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("이미 로그아웃 된 상태입니다.");
        }
        SecurityContextHolder.clearContext();
    }

    public MemberDTO.GetProfile getProfile() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//        if (principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")) {
//            throw new EatingException("회원이 아닙니다.");
//        }
        MemberDTO.GetProfile result = new MemberDTO.GetProfile();
        Member user = memberRepository.findByKakaoId(principal.getPrincipal().toString()).get();
        result.setNickname(user.getNickname());
        result.setTotalCount(user.getTotalCount());
        result.setSugarScore(user.getSugarScore());
        result.setTimeGood(user.getTimeGood());
        result.setNiceGuy(user.getNiceGuy());
        result.setFoodDivide(user.getFoodDivide());
        result.setFastAnswer(user.getFastAnswer());
//        result.setReviews(user.getReviews());
        return result;
    }
    public void editProfile(MemberDTO.EditProfile editInfo) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if (principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")) {
            throw new EatingException("회원이 아닙니다.");
        }
        // kakao id
        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();
        member.setNickname(editInfo.getNickname());
        // member.setProfileUrl(editInfo.getProfileUrl());
        memberRepository.save(member);
    }

    public void editAddress(MemberDTO.EditAddress editInfo) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();
        Location location = new Location();
        location.setAddress(editInfo.getAddress());
        location.setLatitude(editInfo.getLatitude());
        location.setLongitude(editInfo.getLongitude());
        member.setLocation(location);
        memberRepository.save(member);
    }

    public void editFilter(MemberDTO.EditFilter editInfo) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();


        List<Category> memberCategory = member.getMemberCategory();
        List<Category> newMemberCategory = new ArrayList<>();
        if(editInfo.isKorean()) newMemberCategory.add(Category.Korean);
        if(editInfo.isJapanese()) newMemberCategory.add(Category.Japanese);
        if(editInfo.isSchoolfood()) newMemberCategory.add(Category.SchoolFood);
        if(editInfo.isDessert()) newMemberCategory.add(Category.Dessert);
        if(editInfo.isChicken()) newMemberCategory.add(Category.Chicken);
        if(editInfo.isPizza()) newMemberCategory.add(Category.Pizza);
        if(editInfo.isWestern()) newMemberCategory.add(Category.Western);
        if(editInfo.isChinese()) newMemberCategory.add(Category.Chinese);
        if(editInfo.isNightfood()) newMemberCategory.add(Category.NightFood);
        if(editInfo.isFastfood()) newMemberCategory.add(Category.FastFood);
        member.setMemberCategory(newMemberCategory);
        memberRepository.save(member);
    }

//    public void participate(Long id) {
//        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//        String kakao_id = principal.getPrincipal().toString();
//        Member guest = memberRepository.findByKakaoId(kakao_id).get();
//        Post post = postRepository.findById(id).get();
//        List<MemberPost> memberPosts = post.getMemberPosts();
//        MemberPost memberPost = new MemberPost();
//        memberPost.setMember(guest);
//        memberPost.setPost(post);
//        memberPosts.add(memberPost);
//        post.setMemberPosts(memberPosts);
//        postRepository.save(post);
//    }

//    public void unparticipate(Long id) {
//        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//        String kakao_id = principal.getPrincipal().toString();
//        Member guest = memberRepository.findByKakaoId(kakao_id).get();
//        Post post = postRepository.findById(id).get();
//        List<MemberPost> memberPosts = post.getMemberPosts();
//        MemberPost memberPost = new MemberPost();
//        memberPost.setMember(guest);
//        memberPost.setPost(post);
//        memberPosts.remove(memberPost);
//        post.setMemberPosts(memberPosts);
//        postRepository.save(post);
//    }
}
