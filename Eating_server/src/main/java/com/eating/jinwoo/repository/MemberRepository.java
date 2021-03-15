package com.eating.jinwoo.repository;

import com.eating.jinwoo.domain.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Long> {
    public Optional<Member> findByKakaoId(String kakaoId);
}
