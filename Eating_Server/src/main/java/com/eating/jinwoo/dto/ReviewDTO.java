package com.eating.jinwoo.dto;

import lombok.*;

import java.time.LocalDate;

public class ReviewDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class Participate {
        private String kakaoId;
        private String nickName;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class WriteReview {
        private String receiverId;
        private boolean timeGood;
        private boolean niceGuy;
        private boolean foodDivide;
        private boolean fastAnswer;
        private String review;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class GetReview {
        private String SenderNickname;
        private String review;
        private LocalDate writeDate;
    }
}
