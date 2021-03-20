package com.eating.jinwoo.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


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
        private LocalDateTime orderTime;
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
        private Long postId;
        private String title;
        private String foodLink;
        private int deliveryFeeByHost;
        private int meetPlace;
        private int memberCount;
        private String categoryImage;
        private int memberCountLimit;
        private LocalDateTime orderTime;
        private int distance;
        private boolean isFavorite;
    }
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class searchParam {
        private int distance;
        private String category;
        private int page;
        private int size;
    }
}
