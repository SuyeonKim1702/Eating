package com.eating.jinwoo.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

public class MemberDTO {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @Builder
    public static class JoinOrLogin {
        private Long id;
        private String kakaoId;
        private String nickname;
        private MultipartFile profile;
        private String address;
        private Double latitude;
        private Double longitude;
    }
}
