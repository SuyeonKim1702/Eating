package com.eating.jinwoo.repository.postRepository;

import com.eating.jinwoo.domain.Location;
import com.eating.jinwoo.dto.PostDTO;

public interface CustomPostRepository {
    public PostDTO.getPost getPost(Long id);
}
