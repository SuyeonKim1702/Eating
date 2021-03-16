package com.eating.jinwoo.repository.memberRepository;

import com.eating.jinwoo.dto.MemberDTO;

public interface CustomMemberRepository {
    public MemberDTO.GetProfile getProfile(String kakaoId);

}
