package com.eating.jinwoo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review extends BaseAuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn
    private Member sender; //보내는 사람

    @ManyToOne
    @JoinColumn
    private Member member; // 리뷰의 대상자

    private String review;
    private LocalDate writeDate;

    public Review(Member sender, Member member, String review, LocalDate writeDate) {
        this.sender = sender;
        this.member = member;
        this.review = review;
        this.writeDate = writeDate;
    }
}
