package com.eating.jinwoo.config;

import com.eating.jinwoo.domain.Member;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// 스프링 시큐리티가 로그인 요청을 가로채서 로그인 진행 후 완료되면 UserDetails 타입의 오브젝트를
// 스프링 시큐리티 고유한 세션 저장소에 저장한다. 그때 직접 만든 User 객체가 포함되어야 한다.
@Getter // board 정보에 User 정보 넣어주려면 Getter 필요
public class PrincipalDetail implements UserDetails {
    private Member member; // 상속이 아니라 내부에 객체를 들고 있는 것을 composition이라고 함

    // 생성자
    public PrincipalDetail(Member member) {
        this.member = member;
    }
    @Override
    public String getPassword() {
        return member.getPassword();
    }
    @Override
    public String getUsername() {
        return member.getKakao_id();
    }
    @Override // 계정이 만료되지 않았는지를 리턴. true면 만료 안 됨
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override // 계정이 잠기지 않았는지를 리턴. true면 안 잠겨있음
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override // 비밀번호가 만료되지 않았는지를 리턴. true면 만료 안 됨
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override // 계정 활성화(사용가능) 상태인지 리턴. true면 활성화 됨
    public boolean isEnabled() {
        return true;
    }
    @Override // 계정의 Role을 리턴.
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collectors = new ArrayList<>(); // ArrayList가 Collection을 상속하고 있다.
        collectors.add(() -> {return "ROLE_USER";});
        return collectors;
    }
}