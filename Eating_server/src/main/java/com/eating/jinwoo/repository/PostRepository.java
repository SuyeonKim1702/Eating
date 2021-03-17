package com.eating.jinwoo.repository;

import com.eating.jinwoo.domain.Member;
import com.eating.jinwoo.domain.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> {
}
