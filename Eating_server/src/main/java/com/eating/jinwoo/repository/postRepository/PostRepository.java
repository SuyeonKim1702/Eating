package com.eating.jinwoo.repository.postRepository;

import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.Post;
import com.eating.jinwoo.dto.PostDTO;
import lombok.*;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post, Long>, CustomPostRepository {

}
