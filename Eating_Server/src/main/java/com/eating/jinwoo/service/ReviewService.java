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


    public List<ReviewDTO.Participate> getHost() {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String my_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(my_id).get();

        List<ReviewDTO.Participate> hostList = new ArrayList<>();

        List<MemberPost> memberPosts = member.getMemberPosts();
        for (MemberPost mp : memberPosts) {
            ReviewDTO.Participate hostInfo = new ReviewDTO.Participate();
            Post post = mp.getPost();
            boolean finished = post.isFinished();
            LocalDateTime orderTime = post.getOrderTime();
            LocalDateTime now = LocalDateTime.now();


            String postHost_id = post.getHost().getKakaoId();
            // 게시글의 작성자가 내가 아니고 리뷰를 보낸적이 없는 경우
            if(!postHost_id.equals(my_id) && mp.isSendReview() == false){
                // 주문시간이 이미 지났거나 게시글이 완료 상태인 경우
                if(orderTime.isBefore(now) || finished) {
                    hostInfo.setPostId(post.getId());
                    hostInfo.setNickName(post.getHost().getNickname());
                    hostInfo.setKakaoId(post.getHost().getKakaoId());
                    hostList.add(hostInfo);
                    mp.setSendReview(true);
                }
            }
        }
        return hostList;
    }

    // 로그인한 사람이 Host에게
    public void writeReview(ReviewDTO.WriteReview reviewInfo) {

        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String sender_id = principal.getPrincipal().toString();
        String receiver_id = reviewInfo.getReceiverId();
        Member sender = memberRepository.findByKakaoId(sender_id).get();

        Member receiver = memberRepository.findByKakaoId(receiver_id).get();
        // 당도 설정
        double sugarScore = receiver.getSugarScore();
        int reviewScore = reviewInfo.getReviewScore();
        if (reviewScore == 0) {
            receiver.setSugarScore(sugarScore - 1);
        } else if (reviewScore == 2) {
            receiver.setSugarScore(sugarScore + 1);
        }

        // 거래횟수
        long totalCount = receiver.getTotalCount();
        receiver.setTotalCount(totalCount + 1);

        // 객관식 리뷰
        if(reviewInfo.isTimeGood()) {
            int timeGood = (receiver.getTimeGood());
            receiver.setTimeGood(timeGood + 1);
        }
        if(reviewInfo.isNiceGuy()) {
            int niceGuy = receiver.getNiceGuy();
            receiver.setNiceGuy(niceGuy + 1);
        }
        if(reviewInfo.isFoodDivide()) {
            int foodDivide = receiver.getFoodDivide();
            receiver.setFoodDivide(foodDivide + 1);
        }
        if(reviewInfo.isFastAnswer()) {
            int fastAnswer = receiver.getFastAnswer();
            receiver.setFastAnswer(fastAnswer + 1);
        }

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