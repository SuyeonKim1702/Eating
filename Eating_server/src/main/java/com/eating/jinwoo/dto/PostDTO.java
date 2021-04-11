package com.eating.jinwoo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
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
        private String writer;
        private String description;
        private String chatLink;
        private String foodLink;
        private int category;
        private String categoryURL;
        private int currentMemberCount;
        private int memberCountLimit;
        private LocalDateTime orderTime;
        private int meetPlace;
        private int deliveryFeeByHost;
        private boolean finished;
        private double sugerScore;
        private boolean isFavorite;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class editPost {
        private String title;
        private String description;
        private String chatLink;
        private String foodLink;
        private int categoryIdx;
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
    public static class writePost {
        private String title;
        private String description;
        private String chatLink;
        private String foodLink;
        private int category;
        private int memberCountLimit;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
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
        private int categoryIdx;
        private int memberCountLimit;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
        @JsonDeserialize(using = LocalDateTimeDeserializer.class)
        @JsonSerialize(using = LocalDateTimeSerializer.class)
        private LocalDateTime orderTime;
        private int distance;
        private boolean isFavorite;
        private boolean isMine;
        private boolean finished;
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
        private int mine; // 1이면 내꺼, 0이면 남꺼
        private int finished;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    public static class editMemberCount {
        private int memberCount;
    }
}
