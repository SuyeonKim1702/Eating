package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.Location;
import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.Post;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void login(MemberDTO.Login userInfo) {
        memberRepository.findByKakaoId(userInfo.getKakaoId())
                .ifPresentOrElse(
                        this::doLogin, () -> {
                            throw new EatingException("로그인 정보가 없습니다.");
                        }
                );
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
        Member user = memberRepository.findByKakaoId((String) principal.getPrincipal()).get();
        result.setNickname(user.getNickname());
        result.setTotalCount(user.getTotalCount());
        result.setSugarScore(user.getSugarScore());
        result.setTimeGood(user.getTimeGood());
        result.setNiceGuy(user.getNiceGuy());
        result.setFoodDivide(user.getFoodDivide());
        result.setFastAnswer(user.getFastAnswer());
        result.setReviews(user.getReviews());
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
}
