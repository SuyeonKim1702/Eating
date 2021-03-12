package com.eating.jinwoo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Member extends BaseAuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String kakao_id;
    private String nickname;
    private String profile_url;
    private String password;

    private int distance; // 반경. 초기값 500

    private Long total_count; // 거래 카운트

    private int time_good; // 시간이 빨라요
    private int nice_guy; // 친절해요
    private int food_divide; // 음식을 잘 나눠요
    private int fast_answer; // 응답이 빨라요

    private double sugar_score; // 당도. 임의로 0~100 중 50을 초기값 갖도록 설정

    @OneToMany(mappedBy = "member")
    private List<Review> reviews = new ArrayList<>();

    @OneToOne
    private Location location;

    @OneToOne
    private Category category;

    @OneToMany(mappedBy = "member") // member와 post는 다대다 관계이므로 MemberPost라는 중간 테이블 생성함
    private List<MemberPost> memberPosts = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Favorite> favoritePosts = new ArrayList<>();

    public Member(String kakao_id, String nickname, String profile_url, String password) {
        this.kakao_id = kakao_id;
        this.nickname = nickname;
        this.profile_url = profile_url;
        this.password = password;
        this.distance = 500;
        this.total_count = 0L;
        this.time_good = 0;
        this.nice_guy = 0;
        this.food_divide = 0;
        this.fast_answer = 0;
        this.sugar_score = 50.0;
    }
}
