package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.Category;
import com.eating.jinwoo.domain.Location;
import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.security.Principal;

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
                System.out.println("나는 = " + principal.getPrincipal());
                throw new EatingException("이미 로그인 된 상태입니다.");
            }
        }

        memberRepository.findByKakaoId(userInfo.getKakaoId())
                .ifPresentOrElse(
                        this::doLogin, () -> {
                            // 없다면 회원가입 후 로그인
                            Member member = new Member();
                            member.setKakaoId(userInfo.getKakaoId());
                            member.setNickname(userInfo.getNickname());
                            member.setPassword(passwordEncoder.encode(userInfo.getKakaoId()));

                            Location location = new Location();
                            location.setAddress(userInfo.getAddress());
                            location.setLongitude(userInfo.getLongitude());
                            location.setLatitude(userInfo.getLatitude());
                            member.setLocation(location);

                            Category category = new Category();
                            member.setCategory(category);
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
        if(principal == null){
            throw new EatingException("이미 로그아웃 된 상태입니다.");
        }
        else if (principal != null && principal.getPrincipal() == "anonymousUser"){
            throw new EatingException("이미 로그아웃 된 상태입니다.");
        }
        SecurityContextHolder.clearContext();
    }
}
