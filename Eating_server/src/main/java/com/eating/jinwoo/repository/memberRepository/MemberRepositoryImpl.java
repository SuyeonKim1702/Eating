package com.eating.jinwoo.repository.memberRepository;

import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.QMember;
import com.eating.jinwoo.domain.QReview;
import com.eating.jinwoo.domain.Review;
import com.eating.jinwoo.dto.MemberDTO;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public class MemberRepositoryImpl extends QuerydslRepositorySupport implements CustomMemberRepository {

    public MemberRepositoryImpl() {
        super(Member.class);
    }

    @Override
    public MemberDTO.GetProfile getProfile(String kakaoId) {
        QMember member = QMember.member;
        QReview review = QReview.review1;

        JPQLQuery<Member> query = from(member);
        JPQLQuery<Tuple> tuple = query.select(member.nickname, member.profileUrl, member.totalCount, member.sugarScore, member.timeGood,
                member.niceGuy, member.foodDivide, member.fastAnswer, review).where(member.kakaoId.eq(kakaoId))
                .leftJoin(review).on(member.eq(review.member)).orderBy(review.createdDate.desc()).limit(3).offset(0);

        List<Tuple> profile = tuple.fetch();
        MemberDTO.GetProfile result = new MemberDTO.GetProfile();
        List<Review> reviews = new ArrayList<>(3);
        for (Tuple res : profile) {
            reviews.add(res.get(review));
        }
        result.setNickname(profile.get(0).get(member.nickname));
//        result.setProfileUrl(profile.get(0).get(member.profileUrl));
        result.setTotalCount(profile.get(0).get(member.totalCount));
        result.setSugarScore(profile.get(0).get(member.sugarScore));
        result.setTimeGood(profile.get(0).get(member.timeGood));
        result.setNiceGuy(profile.get(0).get(member.niceGuy));
        result.setFoodDivide(profile.get(0).get(member.foodDivide));
        result.setFastAnswer(profile.get(0).get(member.fastAnswer));
        result.setReviews(reviews);

        return result;
    }
}
