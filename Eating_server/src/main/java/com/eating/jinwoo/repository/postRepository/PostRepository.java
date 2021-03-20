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

    @Query(value = "SELECT p.id, p.title, p.food_link, p.delivery_fee_by_host, p.meet_place, count(mp.id) as memberCount, p.member_count_limit, p.order_time, " +
            "(6371*acos(cos(radians(l.latitude)) *cos(radians(p.latitude))*cos(radians(p.longitude) -radians(l.longitude))+sin(radians(l.latitude))*sin(radians(p.latitude)))) AS distance," +
            "count(fou.id) as isFavorite " +
            "FROM post p left outer join " +
            "(select favorite.* from favorite join member on member.id=favorite.member_id where member.kakao_id=:kakao_id) as fou on fou.post_id=p.id" +
            ", member_post mp, " +
            "member m join location l on m.location_id " +
            "where p.category in :categories and m.kakao_id=:kakao_id and mp.post_id = p.id " +
            "HAVING distance <= :distance " +
            "ORDER BY distance limit :page, :size", nativeQuery = true)
    List<Object[]> getPostList(@Param("categories") String[] categories, @Param("distance") int distance, @Param("page") int page, @Param("size") int size,
                     @Param("kakao_id") String kakao_id);
}
