package com.eating.jinwoo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseAuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String chatLink; // 카카오톡 링크

    private String foodLink; // 음식 링크

    private LocalDateTime orderTime; // 주문할 시간

    private boolean deliveryFeeByHost; // true면 호스트가 부담

    @Column(nullable = false)
    private int memberCountLimit; // 같이 먹을 사람 수(host 숫자를 포함한 수)

    // 게시글의 위도, 경도(host의 위도 경도로 설정하면 될 듯)
    @Column(nullable = false)
    private Double longitude;
    @Column(nullable = false)
    private Double latitude;

    @Enumerated(EnumType.STRING)
    private MeetPlace meetPlace;

    @OneToOne
    private Member host;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "post") // post와 member은 다대 다 관계이므로 MemberPost라는 중간 테이블을 생성함
    private List<MemberPost> memberPosts = new ArrayList<>();
}
