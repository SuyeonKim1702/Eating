package com.eating.jinwoo.controller;

import com.eating.jinwoo.dto.ResponseDTO;
import com.eating.jinwoo.dto.ReviewDTO;
import com.eating.jinwoo.service.ReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags={"Review"})
@RestController
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/review/guest/{PostID}")
    @ApiOperation("리뷰 대상자 목록 가져오기")
    public ResponseDTO<List<ReviewDTO.Participate>> getGuest(@PathVariable(value = "PostID") Long id) {
        List<ReviewDTO.Participate> result = reviewService.getGuests(id);
        return new ResponseDTO<>(HttpStatus.OK, "리뷰 대상자 목록 가져오기", result);
    }

    @PostMapping("/review")
    @ApiOperation("리뷰 작성")
    public ResponseDTO<String> writeReview(@RequestBody ReviewDTO.WriteReview reviewInfo) {
        reviewService.writeReview(reviewInfo);
        return new ResponseDTO<>(HttpStatus.OK, "리뷰 작성 완료", null);
    }

    @GetMapping("/review")
    @ApiOperation("주관식 리뷰 불러오기")
    public ResponseDTO<List<ReviewDTO.GetReview>> getReviews() {
        List<ReviewDTO.GetReview> result = reviewService.getReviews();
        return new ResponseDTO<>(HttpStatus.OK, "리뷰 가져오기 완료", result);
    }
}
