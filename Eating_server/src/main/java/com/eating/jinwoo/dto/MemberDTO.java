package com.eating.jinwoo.dto;

import com.eating.jinwoo.domain.Review;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class MemberDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class Join {
        private String kakaoId;
        private String nickname;
        // private MultipartFile profile;
        private String address;
        private Double latitude;
        private Double longitude;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class Login {
        private String kakaoId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class LoginResponse {
        private String address;
        private double latitude;
        private double longitude;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class GetProfile {
        private String nickname;
        // private String profileUrl;
        private long totalCount;
        private double sugarScore;
        private int timeGood;
        private int niceGuy;
        private int foodDivide;
        private int fastAnswer;
//        private List<Review> reviews = new ArrayList<>();
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class EditProfile {
        private String nickname;
        // private String profileUrl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class EditAddress {
        private String address;
        private double longitude;
        private double latitude;
        // private String profileUrl;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class EditFilter {
        private boolean korean;
        private boolean japanese;
        private boolean schoolfood;
        private boolean dessert;
        private boolean chicken;
        private boolean pizza;
        private boolean western;
        private boolean chinese;
        private boolean nightfood;
        private boolean fastfood;
        
    }

}
