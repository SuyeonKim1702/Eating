//package com.eating.jinwoo.repository.postRepository;
//
//import com.eating.jinwoo.domain.Location;
//import com.eating.jinwoo.domain.Member;
//import com.eating.jinwoo.domain.Post;
//import com.eating.jinwoo.dto.PostDTO;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//public class MemoryPostRepository implements PostRepository{
//
//    Map<Long, Post> postMap = new HashMap<>();
//
//    static Long the_id = 0L;
//
//    @Override
//    public PostDTO.getPost getPost(Long id) {
//        return null;
//    }
//
//
//    @Override
//    public <S extends Post> S save(S entity) {
//        entity.setId(++the_id);
//        postMap.put(the_id, entity);
//        return null;
//    }
//
//    @Override
//    public <S extends Post> Iterable<S> saveAll(Iterable<S> entities) {
//        return null;
//    }
//
//    @Override
//    public Optional<Post> findById(Long aLong) {
//        return Optional.ofNullable(postMap.get(aLong));
//    }
//
//    @Override
//    public boolean existsById(Long aLong) {
//        return false;
//    }
//
//    @Override
//    public Iterable<Post> findAll() {
//        return postMap.values();
//    }
//
//    @Override
//    public Iterable<Post> findAllById(Iterable<Long> longs) {
//        return null;
//    }
//
//    @Override
//    public long count() {
//        return 0;
//    }
//
//    @Override
//    public void deleteById(Long aLong) {
//
//    }
//
//    @Override
//    public void delete(Post entity) {
//
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Post> entities) {
//
//    }
//
//    @Override
//    public void deleteAll() {
//        postMap.clear();
//        the_id = 0L;
//    }
//
//    @Override
//    public List<Object[]> getPostListAll(String[] categories, int distance, int page, int size, String kakao_id) {
//        return null;
//    }
//
//    @Override
//    public List<Object[]> getPostListMine(String[] categories, int distance, int page, int size, String kakao_id) {
//        return null;
//    }
//
//    @Override
//    public int getPostMemberCount(Long postId) {
//        return 0;
//    }
//
//    @Override
//    public int getIsFavorite(Long postId, Long memberId) {
//        return 0;
//    }
//
//}
