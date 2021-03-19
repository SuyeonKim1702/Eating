package com.eating.jinwoo.dto;

import lombok.*;

import java.time.LocalDate;


public class PostDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class getPost {
        private String title;
        private String description;
        private String chatLink;
        private String foodLink;
        private int category;
        private int memberCountLimit;
        private LocalDate orderTime;
        private int meetPlace;
        private int deliveryFeeByHost;
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class searchPost {
        private int distance;
        private int size;
        private int page;
    }
        // response
//        private String title;
//        private String foodLink;
//        private int deliveryFeeByHost;
//        private int meetPlace;
//        private int currentMemberCount;
//        private int memberCountLimit;
//        private String orderTime;
//        private String categoryImage;
        @Getter
        @Setter
        @AllArgsConstructor
        @NoArgsConstructor
        @ToString
        @Builder
        public static class deletePost {
            private Long id;
        }


}
