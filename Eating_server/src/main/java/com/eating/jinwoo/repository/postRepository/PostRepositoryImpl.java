package com.eating.jinwoo.repository.postRepository;

import com.eating.jinwoo.domain.Category;
import com.eating.jinwoo.domain.Location;
import com.eating.jinwoo.domain.Post;
import com.eating.jinwoo.domain.QPost;
import com.eating.jinwoo.dto.PostDTO;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.PredicateTemplate;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {



    public PostRepositoryImpl() {
        super(Post.class);
    }

    @Override
    public PostDTO.getPost getPost(Long id) {
        return null;
    }
}
