package com.eating.jinwoo.repository;

import com.eating.jinwoo.domain.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
