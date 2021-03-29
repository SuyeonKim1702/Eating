package com.eating.jinwoo.service;


import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.MemberPost;
import com.eating.jinwoo.domain.Post;
import com.eating.jinwoo.domain.Review;
import com.eating.jinwoo.dto.ReviewDTO;
import com.eating.jinwoo.repository.ReviewRepository;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import com.eating.jinwoo.repository.postRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    private final ReviewRepository reviewRepository;

    public List<ReviewDTO.Participate> getGuests(Long id) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        Post post = postRepository.findById(id).get();
        String my_id = principal.getPrincipal().toString();
        List<MemberPost> memberPosts = post.getMemberPosts();
        List<ReviewDTO.Participate> guestList = new ArrayList<>();

        for (MemberPost member : memberPosts) {
            ReviewDTO.Participate guest = new ReviewDTO.Participate();
            String guest_id = member.getMember().getKakaoId();
            if (my_id.equals(guest_id)) { // 자신 제외
                continue;
            } else {
                guest.setKakaoId(guest_id);
                guest.setNickName(member.getMember().getNickname());
                guestList.add(guest);
            }
        }
        return guestList;
    }

    // 로그인한 사람이 receiver에게
    public void writeReview(ReviewDTO.WriteReview reviewInfo) {

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String sender_id = principal.getPrincipal().toString();
        String receiver_id = reviewInfo.getReceiverId();
        Member sender = memberRepository.findByKakaoId(sender_id).get();
        Member receiver = memberRepository.findByKakaoId(receiver_id).get();

        // 거래횟수 + 객관식 리뷰
        long totalCount = receiver.getTotalCount();
        receiver.setTotalCount(totalCount + 1);
        double sugarScore = receiver.getSugarScore();
        double plusPoint = 0;
        if(reviewInfo.isTimeGood()) {
            int timeGood = (receiver.getTimeGood());
            receiver.setTimeGood(timeGood + 1);
            plusPoint += 0.5;
        }
        if(reviewInfo.isNiceGuy()) {
            int niceGuy = receiver.getNiceGuy();
            receiver.setNiceGuy(niceGuy + 1);
            plusPoint += 0.5;
        }
        if(reviewInfo.isFoodDivide()) {
            int foodDivide = receiver.getFoodDivide();
            receiver.setFoodDivide(foodDivide + 1);
            plusPoint += 0.5;
        }
        if(reviewInfo.isFastAnswer()) {
            int fastAnswer = receiver.getFastAnswer();
            receiver.setFastAnswer(fastAnswer + 1);
            plusPoint += 0.5;
        }
        receiver.setSugarScore(sugarScore + plusPoint);


//        String dateStr = res[6].toString().substring(0, 19);
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
//        result.setOrderTime(dateTime);


        // 주관식 리뷰
        if (reviewInfo.getReview() != null) {
            //날짜 설정
            LocalDate writeDate = LocalDate.now();
            Review review = new Review();
            review.setWriteDate(writeDate);
            review.setMember(receiver);
            review.setSender(sender);
            review.setReview(reviewInfo.getReview());
            List<Review> reviews = receiver.getReviews();
            reviews.add(review);
            receiver.setReviews(reviews);
            memberRepository.save(receiver);
        }
    }

    public List<ReviewDTO.GetReview> getReviews() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();
        List<Review> reviews = member.getReviews();
        List<ReviewDTO.GetReview> reviewList = new ArrayList<>();
        for (Review review: reviews) {
            ReviewDTO.GetReview subjectiveReview = new ReviewDTO.GetReview();
            String senderNickname = review.getSender().getNickname();
            String reviewContent = review.getReview();
            LocalDate writeDate = review.getWriteDate();
            subjectiveReview.setSenderNickname(senderNickname);
            subjectiveReview.setReview(reviewContent);
            subjectiveReview.setWriteDate(writeDate);
            reviewList.add(subjectiveReview);
        }
        return reviewList;
    }


}
