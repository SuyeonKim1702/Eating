package com.eating.jinwoo.repository.memberRepository;

import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.dto.MemberDTO;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    public Optional<Member> findByKakaoId(String kakaoId);

    public MemberDTO.GetProfile getProfile(String kakaoId);
}
