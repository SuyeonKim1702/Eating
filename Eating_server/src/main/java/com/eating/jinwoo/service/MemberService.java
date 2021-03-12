package com.eating.jinwoo.service;

import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void joinOrLogin(MemberDTO.JoinOrLogin userInfo) {

    }
}
