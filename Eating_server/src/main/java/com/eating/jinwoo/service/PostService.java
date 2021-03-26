package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.*;
import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import com.eating.jinwoo.repository.postRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    public void writePost(PostDTO.getPost postInfo) {

        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        if (postInfo.getTitle() == null || postInfo.getDescription() == null ||
                postInfo.getChatLink() == null) {
            throw new EatingException("필수 정보를 입력해주세요");
        }

        // kakao id
        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();

        Post post = new Post();
        post.setTitle(postInfo.getTitle());
        post.setDescription(postInfo.getDescription());
        post.setChatLink(postInfo.getChatLink());
        post.setFoodLink(postInfo.getFoodLink());
        post.setOrderTime(postInfo.getOrderTime());
        post.setDeliveryFeeByHost(postInfo.getDeliveryFeeByHost() == 1 ? true : false);
        post.setMemberCountLimit(postInfo.getMemberCountLimit());
        post.setMeetPlace(MeetPlace.getEnumByValue(postInfo.getMeetPlace()));

        post.setHost(member);
        post.setCategory(Category.getEnumByValue(postInfo.getCategory()));
        post.setLongitude(member.getLocation().getLongitude());
        post.setLatitude(member.getLocation().getLatitude());
        postRepository.save(post);
    }

    public void editPost(PostDTO.getPost postInfo, Long id) {
        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        // kakao id
        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();


        postRepository.findById(id).ifPresentOrElse(
                (post) -> {
                    post.setTitle(postInfo.getTitle());
                    post.setDescription(postInfo.getDescription());
                    post.setChatLink(postInfo.getChatLink());
                    post.setFoodLink(postInfo.getFoodLink());
                    post.setOrderTime(postInfo.getOrderTime());
                    post.setDeliveryFeeByHost(postInfo.getDeliveryFeeByHost() == 1 ? true : false);
                    post.setMemberCountLimit(postInfo.getMemberCountLimit());
                    post.setMeetPlace(MeetPlace.getEnumByValue(postInfo.getMeetPlace()));

                    post.setHost(member);
                    post.setCategory(Category.getEnumByValue(postInfo.getCategory()));
                    post.setLongitude(member.getLocation().getLongitude());
                    post.setLatitude(member.getLocation().getLatitude());
                }, () -> {throw new EatingException("수정할 게시글이 없습니다.");}
        );

    }

    public void deletePost(Long id) {
        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        postRepository.findById(id).ifPresentOrElse(
                (post) -> {
                    post.setDeletedDate(LocalDateTime.now());
                }, () -> {throw new EatingException("삭제할 게시글이 없습니다.");}
        );

    }

    //찜하기
    public void setFavorite(Long id) {

        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        Member member = memberRepository.findByKakaoId(principal.getPrincipal().toString()).get();
        postRepository.findById(id).ifPresentOrElse(
                (post) -> {
                    Favorite favorite = new Favorite();
                    favorite.setPost(post);
                    favorite.setMember(member);
                    List <Favorite> favoriteList = member.getFavoritePosts();
                    favoriteList.add(favorite);
                    member.setFavoritePosts(favoriteList);
                }, () -> {throw new EatingException("게시글이 없습니다.");}
        );
    }

    //찜취소
    public void setUnFavorite(Long id) {

        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }

        Member member = memberRepository.findByKakaoId(principal.getPrincipal().toString()).get();
        postRepository.findById(id).ifPresentOrElse(
                (post) -> {
                    Favorite favorite = new Favorite();
                    favorite.setPost(post);
                    favorite.setMember(member);
                    List <Favorite> favoriteList = member.getFavoritePosts();
                    favoriteList.remove(favorite);
                    member.setFavoritePosts(favoriteList);
                }, () -> {throw new EatingException("게시글이 없습니다.");}
        );
    }

    public List<PostDTO.searchPost> getPostList(PostDTO.searchParam param) {
        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
            throw new EatingException("회원이 아닙니다.");
        }
        String[] categoryNums = param.getCategory().split("-");
        String[] categories = new String[categoryNums.length];
        for (int i = 0; i < categories.length; i++){
            categories[i] = Category.getEnumByValue(Integer.valueOf(categoryNums[i])).name();
        }
        List<Object[]> postList = postRepository.getPostList(categories, param.getDistance(), param.getPage(), param.getSize(),
                principal.getName());
        List<PostDTO.searchPost> ret = new ArrayList<>();
        for (Object[] res : postList) {
            PostDTO.searchPost result = new PostDTO.searchPost();
            Long post_id = Long.valueOf(res[0].toString());
            result.setPostId(post_id);
            result.setTitle(res[1].toString());
            result.setFoodLink(res[2].toString());
            result.setDeliveryFeeByHost(Boolean.valueOf(res[3].toString()) == true ? 1 : 0);
            result.setMeetPlace(MeetPlace.getValueByString(res[4].toString()));
            result.setMemberCountLimit(Integer.valueOf(res[5].toString()));

            String dateStr = res[6].toString().substring(0, 19);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(dateStr, formatter);
            result.setOrderTime(dateTime);

            Double dist = Double.valueOf(res[7].toString());
            result.setDistance((int) Math.round(dist.doubleValue() * 1000));

            // member count calc
            int member_count = postRepository.getPostMemberCount(post_id);
            result.setMemberCount(member_count);

            // is_favorite calc
            Long member_id = Long.valueOf(res[8].toString());
            int is_favorite = postRepository.getIsFavorite(post_id, member_id);
            result.setFavorite(is_favorite == 0 ? false : true);
            ret.add(result);
        }
        return ret;
    }

//    public List<PostDTO.searchPost> getPostDetail(Long id) {
//
//    }
}
