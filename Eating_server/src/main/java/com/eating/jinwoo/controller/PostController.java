package com.eating.jinwoo.controller;


import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.dto.ResponseDTO;
import com.eating.jinwoo.service.PostService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Api(tags={"Post"})
@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

//  게시글 작성 : POST (/post)
    @PostMapping("/post")
    @ApiOperation(value = "게시글 작성")
    public ResponseDTO<String> writePost(@RequestBody PostDTO.writePost postInfo) {
        postService.writePost(postInfo);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 작성 완료", null);
    }
//  게시글 수정 : PUT (/post)
    @PutMapping("/post/{id}")
    @ApiOperation(value = "게시글 수정")
    public ResponseDTO<String> editPost(@PathVariable("id") Long id, @RequestBody PostDTO.editPost postInfo) {
        postService.editPost(postInfo, id);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 수정 완료", null);
    }
//  게시글 삭제 : DELETE (/post/{postId})
    @DeleteMapping("/post/{postID}")
    @ApiOperation(value = "게시글 삭제")
    public ResponseDTO<String> delete(@PathVariable("postID") Long id) {
        postService.deletePost(id);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 삭제 완료", null);
    }

//  찜하기 : GET (/favorite/{postId})
    @GetMapping("/favorite/{postId}")
    @ApiOperation(value = "찜하기")
    public ResponseDTO<String> setFavorite(@PathVariable("postId") Long id) {
        postService.setFavorite(id);
        return new ResponseDTO<>(HttpStatus.OK, "찜하기 완료", null);
    }

    //  찜 취소 : GET (/unfavorite/{postId})
    @GetMapping("/unfavorite/{postId}")
    @ApiOperation(value = "게시글 찜하기 취소")
    public ResponseDTO<String> setUnFavorite(@PathVariable("postId") Long id) {
        postService.setUnFavorite(id);
        return new ResponseDTO<>(HttpStatus.OK, "찜 취소 완료", null);
    }

    @GetMapping("/posts")
    @ApiOperation(value = "게시글 목록 가져오기")
    public ResponseDTO<List<PostDTO.searchPost>> getPostList(PostDTO.searchParam param) {
        List<PostDTO.searchPost> posts = postService.getPostList(param);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 목록 가져오기", posts);
    }

    @GetMapping("/posts/favorite")
    @ApiOperation(value = "찜한 게시글 목록 가져오기")
    public ResponseDTO<List<PostDTO.searchPost>> getFavoriteList() {
        List<PostDTO.searchPost> posts = postService.getFavoriteOrParticipate(0);
        return new ResponseDTO<>(HttpStatus.OK, "찜한 게시글 목록 가져오기", posts);
    }

    @GetMapping("/posts/participating")
    @ApiOperation(value = "참여중인 목록 가져오기")
    public ResponseDTO<List<PostDTO.searchPost>> getParticipatingList() {
        List<PostDTO.searchPost> posts = postService.getFavoriteOrParticipate(1);
        return new ResponseDTO<>(HttpStatus.OK, "참여중인 게시글 목록 가져오기", posts);
    }

    @GetMapping("/posts/participated")
    @ApiOperation(value = "참여한 목록 가져오기")
    public ResponseDTO<List<PostDTO.searchPost>> getParticipatedList() {
        List<PostDTO.searchPost> posts = postService.getFavoriteOrParticipate(2);
        return new ResponseDTO<>(HttpStatus.OK, "참여한 게시글 목록 가져오기", posts);
    }

    @GetMapping("/post/done/{postId}")
    @ApiOperation(value = "게시글 상태 변경")
    public ResponseDTO<String> setPostStatus(@PathVariable(value = "postId") Long id) {
        postService.setPostStatus(id);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 상태 변경 완료", null);
    }

    @GetMapping("/post/join/{postId}")
    @ApiOperation(value = "게시글 참여")
    public ResponseDTO<String> joinPost(@PathVariable(value = "postId") Long id) {
        postService.joinPost(id);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 참여 완료", null);
    }

    @GetMapping("post/{postId}")
    @ApiOperation(value = "게시글 상세보기")
    public ResponseDTO<PostDTO.getPost> getPostDetail(@PathVariable(value = "postId") Long id) {
        PostDTO.getPost result = postService.getPostDetail(id);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 상세보기", result);
    }

    @PutMapping("post/membercount/{postId}")
    @ApiOperation(value = "참가 인원 바꾸기")
    public ResponseDTO<String> editMemberCount(@PathVariable(value = "postId") Long id, @RequestBody PostDTO.editMemberCount memberCount) {
        postService.editMemberCount(id, memberCount);
        return new ResponseDTO<>(HttpStatus.OK, "참가인원 바꾸기 완료", null);
    }
}
