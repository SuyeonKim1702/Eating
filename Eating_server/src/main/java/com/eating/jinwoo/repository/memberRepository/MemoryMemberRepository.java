package com.eating.jinwoo.repository.memberRepository;

import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository {
    Map<Long, Member> memberMap = new HashMap<>();

    static Long the_id = 0L;

    @Override
    public Optional<Member> findByKakaoId(String kakao_id) {
        Collection<Member> members = memberMap.values();
        for (Member member : members) {
            if (member.getKakaoId().equals(kakao_id)){
                return Optional.ofNullable(member);
            }
        }
        return Optional.empty();
    }

    @Override
    public MemberDTO.GetProfile getProfile(String kakaoId) {
        return null;
    }

    @Override
    public <S extends Member> S save(S entity) {
        entity.setId(++the_id);
        memberMap.put(the_id, entity);
        return null;
    }

    @Override
    public <S extends Member> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Member> findById(Long aLong) {
        return Optional.ofNullable(memberMap.get(aLong));
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Member> findAll() {
        return memberMap.values();
    }

    @Override
    public Iterable<Member> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Member entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends Member> entities) {

    }

    @Override
    public void deleteAll() {
        memberMap.clear();
        the_id = 0L;
    }
}
