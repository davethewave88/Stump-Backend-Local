package net.javaguides.springboot_backend.domain;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface CommentRepository extends CrudRepository<Comment, Integer> {

    @Query(value = "select * from comment where :post_id = post_id order by comment_id desc", nativeQuery = true)
    List<Comment> getPostComments(int post_id);

    @Query(value = "select * from comment", nativeQuery = true)
    List<Comment> getAllComments();



}
