package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.Location;
import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.dto.MemberDTO;
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

    public void joinOrLogin(MemberDTO.JoinOrLogin userInfo) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal != null){
            if(principal.getPrincipal() != "anonymousUser"){
                throw new EatingException("이미 로그인 된 상태입니다.");
            }
        }
        if (userInfo.getNickname().length() > 8 || userInfo.getNickname().length() < 3) {
            throw new EatingException("닉네임 글자수 제한을 지켜주세요.");
        }
        memberRepository.findByKakaoId(userInfo.getKakaoId())
                .ifPresentOrElse(
                        this::doLogin, () -> {
                            memberRepository.findByNickname(userInfo.getNickname()).ifPresent((member) -> {
                                throw new EatingException("이미 존재하는 닉네임입니다.");
                            });
                            // 없다면 회원가입 후 로그인
                            Member member = new Member();
                            member.setKakaoId(userInfo.getKakaoId());
                            member.setNickname(userInfo.getNickname());
                            member.setPassword(passwordEncoder.encode(userInfo.getKakaoId()));

                            Location location = new Location(userInfo.getAddress(), userInfo.getLongitude(), userInfo.getLatitude());
                            member.setLocation(location);

                            // 프로필 사진 없을수도
                            if(userInfo.getProfile() != null){
                                // 프로필 사진 저장하고 url 가져오는 부분 필요
                                member.setProfileUrl("profile_url");
                            } else {
                                member.setProfileUrl(null);
                            }
                            memberRepository.save(member);
                            doLogin(member);
                        }
                );
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
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        return null;
    }
}
