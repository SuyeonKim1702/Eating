package com.eating.jinwoo.repository.postRepository;

import com.eating.jinwoo.domain.Location;
import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.Post;
import com.eating.jinwoo.dto.PostDTO;
import lombok.*;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long>, CustomPostRepository {

    @Query(value = "SELECT p.id, p.title, p.food_link, p.delivery_fee_by_host, p.meet_place, " +
            "p.member_count_limit, p.order_time, " +
            "(6371*acos(cos(radians(l.latitude)) *cos(radians(p.latitude))*cos(radians(p.longitude) " +
            "-radians(l.longitude))+sin(radians(l.latitude))*sin(radians(p.latitude)))) AS distance, m.id as member_id " +
            "FROM post p, member m, location l " +
            "where p.category in :categories and m.kakao_id=:kakao_id and l.id=m.location_id " +
            "HAVING distance <= :distance " +
            "ORDER BY distance limit :page, :size", nativeQuery = true)
    List<Object[]> getPostList(@Param("categories") String[] categories, @Param("distance") int distance, @Param("page") int page, @Param("size") int size,
                     @Param("kakao_id") String kakao_id);

    @Query("select count(p.id) from Post p join p.memberPosts mp where p.id = :postId")
    int getPostMemberCount(@Param("postId") Long postId)
            ;

    @Query("select count(f.id) from Favorite f join f.post where f.member.id = :memberId and f.post.id = :postId")
    int getIsFavorite(@Param("postId") Long postId, @Param("memberId") Long memberId);
}
