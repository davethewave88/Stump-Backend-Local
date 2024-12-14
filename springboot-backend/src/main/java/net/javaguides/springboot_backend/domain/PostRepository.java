package net.javaguides.springboot_backend.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PostRepository extends CrudRepository<Post, Integer> {

    @Query(value = "select * from post order by post_id desc limit 100", nativeQuery = true)
    List<Post> getAllPosts();

    @Query(value = "select * from post where :id = creator_id order by post_id desc;", nativeQuery = true)
    List<Post> getAllPostsById(int id);

    @Query(value = "select * from post where :id = post_id", nativeQuery = true)
    Post getPostById(int id);

    @Query(value = "select p.post_id, p.creator_id, u.user_id, p.username, p.create_time, p.link, p.title, p.description from post p, users u where u.user_id = p.creator_id order by create_time desc limit 5;;",nativeQuery = true)
    List<Post> getLatest5Posts();

}
