package com.eating.jinwoo.repository.postRepository;

import com.eating.jinwoo.domain.Post;
import com.eating.jinwoo.dto.PostDTO;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class PostRepositoryImpl extends QuerydslRepositorySupport implements CustomPostRepository {

    public PostRepositoryImpl() {
        super(Post.class);
    }

    @Override
    public PostDTO.getPost getPost(Long id) {
        return null;
    }
}
