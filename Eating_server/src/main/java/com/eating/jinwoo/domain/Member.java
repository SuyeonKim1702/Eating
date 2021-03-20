package com.eating.jinwoo.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Member extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kakaoId;
    @Column(length = 8)
    private String nickname;
    private String profileUrl;
    private String password;

    private int distance; // 반경. 초기값 500

    private long totalCount; // 거래 카운트

    private int timeGood; // 시간이 빨라요
    private int niceGuy; // 친절해요
    private int foodDivide; // 음식을 잘 나눠요
    private int fastAnswer; // 응답이 빨라요

    private double sugarScore; // 당도. 임의로 0~100 중 50을 초기값 갖도록 설정

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Review> reviews = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Location location;

    @ElementCollection
    @CollectionTable(name = "memberCategory")
    @MapKeyJoinColumn
    @Enumerated(EnumType.STRING)
    private List<Category> memberCategory = new ArrayList<>();

    @OneToMany(mappedBy = "member") // member와 post는 다대다 관계이므로 MemberPost라는 중간 테이블 생성함
    private List<MemberPost> memberPosts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Favorite> favoritePosts = new ArrayList<>();

    public Member() {
        this.distance = 500;
        this.totalCount = 0;
        this.timeGood = 0;
        this.niceGuy = 0;
        this.foodDivide = 0;
        this.fastAnswer = 0;
        this.sugarScore = 50.0;
        for (int i = 0; i < 10; i++){
            memberCategory.add(Category.getEnumByValue(i));
        }
    }

    public Member(String kakaoId, String nickname, String profileUrl, String password) {
        this();
        this.kakaoId = kakaoId;
        this.nickname = nickname;
        this.profileUrl = profileUrl;
        this.password = password;
    }
}
