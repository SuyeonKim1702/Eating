package com.eating.jinwoo.controller;


import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.dto.ResponseDTO;
import com.eating.jinwoo.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

//  게시글 작성 : POST (/post)
    @PostMapping("/post")
    public ResponseDTO<String> writePost(@RequestBody PostDTO.getPost postInfo) {
        postService.writePost(postInfo);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 작성 완료", null);
    }
//  게시글 수정 : PUT (/post)
    @PutMapping("/post/{id}")
    public ResponseDTO<String> editPost(@PathVariable("id") Long id, @RequestBody PostDTO.getPost postInfo) {
        postService.editPost(postInfo, id);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 수정 완료", null);
    }
//  게시글 삭제 : DELETE (/post/{postId})
    @DeleteMapping("/post/{postID}")
    public ResponseDTO<String> delete(@PathVariable("postID") Long id) {
        postService.deletePost(id);
        return new ResponseDTO<>(HttpStatus.OK, "게시글 삭제 완료", null);
    }

//  찜하기 : GET (/favorite/{postId})
    @GetMapping("/favorite/{postId}")
    public ResponseDTO<String> setFavorite(@PathVariable("postID") Long id) {
        postService.setFavorite(id);
        return new ResponseDTO<>(HttpStatus.OK, "찜하기 완료", null);
    }

    //  찜 취소 : GET (/favorite/{postId})
    @GetMapping("/unfavorite/{postId}")
    public ResponseDTO<String> setUnFavorite(@PathVariable("postID") Long id) {
        postService.setUnFavorite(id);
        return new ResponseDTO<>(HttpStatus.OK, "찜 취소 완료", null);
    }

//  게시글 검색 : GET (/post?distance={dist}&category=0-2-4-6&page=0&size=10)
//  게시글 세부 보기 : GET (/post/{postId})
//    @GetMapping("/post/{postId}")
//    public ResponseDTO<String>

}
