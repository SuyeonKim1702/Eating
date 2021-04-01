package com.eating.jinwoo.service;

import com.eating.jinwoo.common.EatingException;
import com.eating.jinwoo.domain.*;
import com.eating.jinwoo.dto.PostDTO;
import com.eating.jinwoo.repository.FavoriteRepository;
import com.eating.jinwoo.repository.memberRepository.MemberRepository;
import com.eating.jinwoo.repository.postRepository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FavoriteRepository favoriteRepository;

    public void writePost(PostDTO.writePost postInfo) {

        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
//            throw new EatingException("회원이 아닙니다.");
//        }

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
        post.setCategory(Category.getEnumByValue(postInfo.getCategory()));
        post.setMemberCountLimit(postInfo.getMemberCountLimit());
        post.setOrderTime(postInfo.getOrderTime());
        MeetPlace enumByValue = MeetPlace.getEnumByValue(postInfo.getMeetPlace());
        System.out.println("enumByValue = " + enumByValue);
        post.setMeetPlace(enumByValue);
        post.setDeliveryFeeByHost(postInfo.getDeliveryFeeByHost());
        post.setHost(member);
        post.setCategory(Category.getEnumByValue(postInfo.getCategory()));
        post.setLongitude(member.getLocation().getLongitude());
        post.setLatitude(member.getLocation().getLatitude());
        MemberPost memberPost = new MemberPost();
        memberPost.setMember(member);
        memberPost.setPost(post);
        post.setMemberPosts(Arrays.asList(memberPost));
        postRepository.save(post);
    }

    public void editPost(PostDTO.editPost postInfo, Long id) {
        //로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();

//        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
//            throw new EatingException("회원이 아닙니다.");
//        }

        // kakao id
        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();


        postRepository.findById(id).ifPresentOrElse(
                (post) -> {
                    post.setTitle(postInfo.getTitle());
                    post.setDescription(postInfo.getDescription());
                    post.setChatLink(postInfo.getChatLink());
                    post.setFoodLink(postInfo.getFoodLink());
                    int categoryIdx = postInfo.getCategoryIdx();
                    post.setCategory(Category.getEnumByValue(categoryIdx));
                    post.setMemberCountLimit(postInfo.getMemberCountLimit());
                    post.setOrderTime(postInfo.getOrderTime());
                    post.setMeetPlace(MeetPlace.getEnumByValue(postInfo.getMeetPlace()));
                    post.setDeliveryFeeByHost(postInfo.getDeliveryFeeByHost());

                    post.setHost(member);
                    post.setLongitude(member.getLocation().getLongitude());
                    post.setLatitude(member.getLocation().getLatitude());

                }, () -> {throw new EatingException("수정할 게시글이 없습니다.");}
        );

    }

    public void deletePost(Long id) {
        //로그인 되어있는지 확인
//        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
//            throw new EatingException("회원이 아닙니다.");
//        }

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
//        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
//            throw new EatingException("회원이 아닙니다.");
//        }

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
//        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
//            throw new EatingException("회원이 아닙니다.");
//        }

        Member member = memberRepository.findByKakaoId(principal.getPrincipal().toString()).get();
        postRepository.findById(id).ifPresentOrElse(
                (post) -> {
                    List<Favorite> favoriteList = member.getFavoritePosts();
                    List<Favorite> newFavoriteList = new ArrayList<>();
                    Favorite the_delete = null;
                    for (Favorite favorite : favoriteList) {
                        if(favorite.getPost() == post) {
                            the_delete = favorite;
                            continue;
                        }
                        newFavoriteList.add(favorite);
                    }
                    member.getFavoritePosts().clear();
                    member.setFavoritePosts(newFavoriteList);
                    favoriteRepository.delete(the_delete);
                }, () -> {throw new EatingException("게시글이 없습니다.");}
        );
    }

    public List<PostDTO.searchPost> getPostList(PostDTO.searchParam param) {
        // 로그인 되어있는지 확인
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
//        if(principal == null || (principal != null && principal.getPrincipal() == "anonymousUser")){
//            throw new EatingException("회원이 아닙니다.");
//        }
        String[] categoryNums = param.getCategory().split("-");
        String[] categories = new String[categoryNums.length];
        for (int i = 0; i < categories.length; i++){
            categories[i] = Category.getEnumByValue(Integer.valueOf(categoryNums[i])).name();
        }
        List<Object[]> postList = null;
        if (param.getMine() == 1) { // 내가 작성한 게시글
            postList = postRepository.getPostListMine(categories, param.getDistance(), param.getPage(), param.getSize(), principal.getName());
        }
//        if (param.getFinished() == 1) { //완료된 게시글
//            postList = postRepository.getPostListFinished(categories, param.getDistance(), param.getPage(), param.getSize(), principal.getName());
//        }
//        else if (param.getFinished() == 0) { //진행중인 게시글
//            postList = postRepository.getPostListNotFinished(categories, param.getDistance(), param.getPage(), param.getSize(), principal.getName());
//        }
        else { //다 불러오기
            postList = postRepository.getPostListAll(categories, param.getDistance(), param.getPage(), param.getSize(), principal.getName());
        }

        List<PostDTO.searchPost> ret = new ArrayList<>();
        for (Object[] res : postList) {
            PostDTO.searchPost result = new PostDTO.searchPost();
            Long post_id = Long.valueOf(res[0].toString());
            result.setPostId(post_id);
            result.setTitle(res[1].toString());
            result.setFoodLink(res[2].toString());

//            result.setDeliveryFeeByHost(Boolean.valueOf(res[3].toString()) == true ? 1 : 0);
            result.setDeliveryFeeByHost(Integer.valueOf(res[3].toString()));

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
            result.setMemberCount(Integer.valueOf(res[8].toString()));

            // is_favorite calc
            Long member_id = Long.valueOf(res[8].toString());
            int is_favorite = postRepository.getIsFavorite(post_id, member_id);
            result.setFavorite(is_favorite == 0 ? false : true);

            // is_mine calc
            Post post = postRepository.findById(post_id).get();
            Member host = post.getHost();
            String hostname = host.getKakaoId();
            String user = principal.getPrincipal().toString();
            result.setMine(hostname.equals(user) ? true: false);

            // category number calc
            String categoryStr = post.getCategory().toString();
            int ordinal = post.getCategory().ordinal();
            result.setCategoryIdx(ordinal);

            // set finished
            result.setFinished(post.isFinished());
            ret.add(result);
        }
        return ret;
    }

    public List<PostDTO.searchPost> getFavoriteOrParticipate(int param) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String kakao_id = principal.getPrincipal().toString();
        List<Object[]> postList = null;
        if (param == 0) {
            postList =postRepository.getPostFavorite(kakao_id);
        } else if (param == 1) {
            postList = postRepository.getPostParticipating(kakao_id);
        } else {
            postList = postRepository.getPostParticipated(kakao_id);
        }
        List<PostDTO.searchPost> ret = new ArrayList<>();
        for (Object[] res : postList) {
            PostDTO.searchPost result = new PostDTO.searchPost();
            Long post_id = Long.valueOf(res[0].toString());
            result.setPostId(post_id);
            result.setTitle(res[1].toString());
            result.setFoodLink(res[2].toString());
            result.setDeliveryFeeByHost(Boolean.valueOf(res[3].toString()) == true ? 1 : 0);
            MeetPlace.getValueByString(res[4].toString());
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

            // is_mine calc
            Post post = postRepository.findById(post_id).get();
            Member host = post.getHost();
            String hostname = host.getKakaoId();
            String user = principal.getPrincipal().toString();
            result.setMine(hostname.equals(user) ? true: false);

            // category number calc
            String categoryStr = post.getCategory().toString();
            int ordinal = post.getCategory().ordinal();
            result.setCategoryIdx(ordinal);
            ret.add(result);
        }
        return ret;
    }

    public void setPostStatus(Long id) {
        postRepository.findById(id).ifPresentOrElse((post) -> {
            if(post.getDeletedDate() == null) {
                post.setDeletedDate(LocalDateTime.now());
                post.setFinished(true);
                postRepository.save(post);
            } else {
                post.setDeletedDate(null);
                post.setFinished(false);
                postRepository.save(post);
            }


        }, ()-> {
            throw new EatingException("없는 게시물 입니다.");
        });
    }

    public void joinPost(Long id) {
        postRepository.findById(id).ifPresentOrElse((post) -> {
            int before_member_count = postRepository.getPostMemberCount(id);
            if (before_member_count + 1 > post.getMemberCountLimit()) {
                throw new EatingException("이미 꽉찬 게시글입니다.");
            }
            Authentication principal = SecurityContextHolder.getContext().getAuthentication();
            Member member = memberRepository.findByKakaoId(principal.getPrincipal().toString()).get();
            MemberPost memberPost = new MemberPost();
            memberPost.setPost(post);
            memberPost.setMember(member);

            List<MemberPost> memberPosts = member.getMemberPosts();
            memberPosts.add(memberPost);
            member.setMemberPosts(memberPosts);
            memberRepository.save(member);
        }, ()-> {
            throw new EatingException("없는 게시물 입니다.");
        });
    }

    public PostDTO.getPost getPostDetail(Long id) {
        Authentication principal = SecurityContextHolder.getContext().getAuthentication();
        String kakao_id = principal.getPrincipal().toString();
        Member member = memberRepository.findByKakaoId(kakao_id).get();

        PostDTO.getPost result = new PostDTO.getPost();
        Post post = postRepository.findById(id).get();
        result.setTitle(post.getTitle());
        Member host = post.getHost();
        result.setWriter(host.getNickname());
        result.setDescription(post.getDescription());
        result.setChatLink(post.getChatLink());
        result.setFoodLink(post.getFoodLink());
        String baseUrl = "https://s3.ap-northeast-2.amazonaws.com/eating-image/category_b/";
        String categoryNum = Integer.toString(post.getCategory().ordinal());
        result.setCategory(post.getCategory().ordinal());
        result.setCategoryURL(baseUrl + categoryNum + ".png");

        result.setCurrentMemberCount(post.getCurrentMemberCount());

        result.setMemberCountLimit(post.getMemberCountLimit());
        result.setOrderTime(post.getOrderTime());
        result.setMeetPlace(post.getMeetPlace().ordinal());
        result.setDeliveryFeeByHost(post.getDeliveryFeeByHost());
        result.setFinished(post.isFinished());
        result.setSugerScore(host.getSugarScore());
        int is_favorite = postRepository.getIsFavorite(id, member.getId());
        result.setFavorite(is_favorite == 1 ? true : false);
        return result;
    }

    public void editMemberCount(Long id, PostDTO.editMemberCount memberCount) {
        Post post = postRepository.findById(id).get();
        int modifiedMemberCount = memberCount.getMemberCount();
        if (modifiedMemberCount < 0 || modifiedMemberCount >= 5) {
            throw new EatingException("올바른 인원을 입력해주세요");
        } else {
            post.setCurrentMemberCount(modifiedMemberCount);
            postRepository.save(post);
        }

    }
}
