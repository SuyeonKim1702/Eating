package com.eating.jinwoo.repository;

import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.Review;
import com.eating.jinwoo.dto.MemberDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@EnableJpaAuditing
@Transactional
public class MemberRepositoryTests {
    @PersistenceContext
    EntityManager em;

    @Autowired
    private MemberRepository memberRepository;

    private Member member;
    private Member sender;

    @BeforeEach
    void beforeEach() {
        sender = new Member("babo", "hahaman", "wjtykj.wegeg", "qwehrqehjrejrejreh3h4h34haerwhawe");
        memberRepository.save(sender);

        member = new Member("kakao", "nickname", "woqoigw.coijweg", "oqwiehgoi32123oih32oihegqgohuxag");
        memberRepository.save(member);
    }

    @Test
    void getProfile() {
        // given
        Member the_member = memberRepository.findByKakaoId("kakao").get();
        Review review = new Review(sender, member, "holy shit~~~ perfect guy");
        Review review1 = new Review(sender, member, "second review");
        Review review2 = new Review(sender, member, "third review");
        Review review3 = new Review(sender, member, "fourth review");
        List<Review> reviews = Arrays.asList(review, review1, review2, review3);
        the_member.setReviews(reviews);
        em.flush();

        // when
        MemberDTO.GetProfile profile = memberRepository.getProfile("kakao");

        // then
        assertThat(profile.getNickname()).isEqualTo(member.getNickname());
//        assertThat(profile.getProfileUrl()).isEqualTo(member.getProfileUrl());
        assertThat(profile.getTotalCount()).isEqualTo(member.getTotalCount());
        assertThat(profile.getSugarScore()).isEqualTo(member.getSugarScore());
        assertThat(profile.getTimeGood()).isEqualTo(member.getTimeGood());
        assertThat(profile.getNiceGuy()).isEqualTo(member.getNiceGuy());
        assertThat(profile.getFoodDivide()).isEqualTo(member.getFoodDivide());
        assertThat(profile.getFastAnswer()).isEqualTo(member.getFastAnswer());
        assertThat(profile.getReviews().size()).isEqualTo(3);
        assertThat(profile.getReviews().get(0).getSender().getId()).isEqualTo(sender.getId());
    }
}
