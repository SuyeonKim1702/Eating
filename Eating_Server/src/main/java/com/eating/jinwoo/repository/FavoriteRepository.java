package com.eating.jinwoo.repository;

import com.eating.jinwoo.domain.Favorite;
import org.springframework.data.repository.CrudRepository;

public interface FavoriteRepository extends CrudRepository<Favorite, Long> {
}
